import { KeyboardEvent ,  useEffect, useRef, useState } from "react";
import ChatRoomMembers from "../components/ChatRoomMembers";
import SockJs from 'sockjs-client';
import { Client } from "@stomp/stompjs";
import { useNavigate, useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../store/store";
import { ChatMessage, Member } from "../type/type";
import axios from "axios";
import Messages from "../components/Messages";

export default function ChattingRoom(){

    // 웹소켓 state
    const [webSocket , setWebSocket] = useState<Client>();

    // 서버 url
    const url = 'http://localhost:8083/chatApp';

    // param
    const {chatRoomNo} = useParams();

    // 채팅 메세지 저장 state
    const [message , setMessage] = useState('');

    // textarea 주소값을 저장할 ref
    const textareaRef = useRef<HTMLTextAreaElement>(null);

    //
    const bottomRef = useRef<HTMLLIElement>(null);

    // 현재 접속중인 user 정보
    const user = useSelector((state:RootState) => state.user);

    // 현재 채팅방 메세지 state
    const [chatMessage , setChatMessage] = useState<ChatMessage[]>([]);

    // 현재 채팅방 멤버들 저장할 state
    const [chatRoomMembers , setChatRoomMembers] = useState<Member[]>([]);

    // navigate 함수
    const navi = useNavigate();
    
    // 웹소켓 연결 -> STOMP
    useEffect(() => {
        // npm i --save @types/sockjs-client
        const createWebSocket = () => new SockJs(url+"/stompServer");

        // 웹소켓 연결 설정
        const stompClient = new Client({
            webSocketFactory : createWebSocket , 
            reconnectDelay : 10000, // 10sec
            // 생성시 실행할 함수
            onConnect : (frame) => { // frame : 메세지영역?
                console.log(frame);
                // 구독 , 발행
                // 구독 1. 현재 채팅방에 메세지가 발행되는 경우
                stompClient.subscribe(`/chat/chatRoomNo/${chatRoomNo}/message` , (frame) => {
                    const message = JSON.parse(frame.body);

                    // 의존성배열이 비어있는 useEffect 함수 내부에서
                    // state값은 항상 "초기 렌더링시의 값" 을 유지한다.
                    // setChatMessage([...chatMessage , message]);

                    // prevState 을 통해 이전 state 값을 받아옴
                    setChatMessage((prevState) => {
                        return [...prevState , message];
                    })
                });

                // 구독 2. 채팅방에 새로운 사용자가 입장
                stompClient.subscribe(`/chat/chatRoomNo/${chatRoomNo}/newMember` , (frame) => {
                    console.log(frame.body);
                    let user = JSON.parse(frame.body);
                    setChatRoomMembers((prevState) =>{
                        let filterArr = prevState.filter((u) => u.userNo !== user.userNo)
                        return [...filterArr , user];
                    })
                });

                // 구독 3. 채팅방 사용자가 나가는 경우
                stompClient.subscribe(`/chat/chatRoomNo/${chatRoomNo}/exitMember` , (frame) => {
                   let user = JSON.parse(frame.body);
                   setChatRoomMembers((prevState) =>{
                        return prevState.filter((member) => member.userNo !== user.userNo);
                    })
                });

                // 구독 4. 채팅방에 접속중인 다른 사용자의 상태값(1 온라인 , 2 오프라인)이 바뀔때
                stompClient.subscribe(`/chat/chatRoomNo/${chatRoomNo}/updateStatus` , (frame) => {
                    // 바뀐유저정보
                    // userStatus , userNo
                    let user = JSON.parse(frame.body);

                    setChatRoomMembers((prev) => {
                        return prev.map((member) => {
                            if(member.userNo == user.userNo){
                                return user;
                            }else{
                                return member;
                            }
                        })
                    })
                })

                // 발행 1. CHAT_ROOM_JOIN 테이블에 참여자 정보 추가
                stompClient.publish({
                    destination : `/chat/chatRoomJoin/${chatRoomNo}/${user.userNo}/newMember`,
                    body : JSON.stringify({chatRoomNo , userNo : user.userNo})
                })

                // 발행 2. 유저 상태값 변경
                stompClient.publish({
                    destination : `/chat/chatRoomJoin/chatRoomNo/${chatRoomNo}/updateStatus`,
                    body : JSON.stringify({
                        ...user , 
                        userStatus : 1
                    })
                })
            }

        });

        // 웹소켓 활성화
        stompClient.activate(); 
        setWebSocket(stompClient);

        // 채팅방 메세지 가져오기
        axios.get(`${url}/chatMessage/chatRoomNo/${chatRoomNo}`)
            .then((response) => {
                setChatMessage(response.data);
            });

        // 채팅방 참여자 목록 가져오기
        axios.get(`${url}/chatRoomJoin/chatRoomNo/${chatRoomNo}`)
            .then((response) => {
                setChatRoomMembers(response.data);
        });

        setTimeout(() => {
            scollToBottom()
        }, 100);

        return () => {
            // 컴포넌트 소멸시 웹소켓 해제
            stompClient.publish({
                destination : `/chat/chatRoomJoin/chatRoomNo/${chatRoomNo}/updateStatus`,
                body : JSON.stringify({
                    ...user , 
                    userStatus : 2
                })
            })
            stompClient.deactivate(); // 웹소켓 비활성화
        }

    },[]);

    const submitMessage = (e:KeyboardEvent) => {
        if(e.key === 'Enter' && !e.shiftKey){ //shift+enter 허용?
            e.preventDefault(); // 기본 이벤트 호출 방지
            sendMessage();
            //textareaRef.current.value = "";
        }
    }

    const sendMessage = () => {
        const chatMessage = {
            message , // message = message
            chatRoomNo , // = chatRoomNo : chatRoomNo
            userNo : user.userNo
        }

        if(!message){
            alert("뭐든 입력하세요.");
            return;
        }

        if(!user){
            alert("로그인 후 이용해주세요.");
            return;
        }

        if(!webSocket){
            alert("웹소켓 연결중입니다.");
            return;
        }

        webSocket
            .publish({
                destination : `/chat/sendMessage/chatRoomNo/${chatRoomNo}`, // 발행 목적지 - 중계자를 거쳐서 이동?
                headers : {},
                body : JSON.stringify(chatMessage) // 실제 데이터 영역 (자바 객체 -> JSON 문자열 형태)
            })

            setTimeout(() => {
                scollToBottom()
            }, 100);

            setMessage('');
    }

    const exitChatRoom = () => {
        webSocket?.publish({
            destination : `/chat/chatRoomJoin/${chatRoomNo}/${user.userNo}/delete`,
            body : JSON.stringify(user)
        });
        setTimeout(()=> { // 비동기함수
            navi("/");
        },100)
    }

    const scollToBottom = () => {
        if(bottomRef.current){
            bottomRef.current.scrollIntoView({behavior:'smooth'});
        }
    }

    return (
        <div className = "main">
            {/* 1. 채팅방 참여자 목록 */}
            <ChatRoomMembers chatRoomMembers={chatRoomMembers}/>
            <div className = "chatting-area">

                <div className="chat-header">
                    <button className="btn btn-outline-danger" onClick={exitChatRoom}>나가기</button>
                </div>

                {/* 채팅내용 */}
                <ul className="display-chatting">
                    <Messages chatMessages={chatMessage} />
                    <li ref={bottomRef}></li>
                </ul>

                <div className="input-area">
                    <textarea rows={3} name="message"
                        ref={textareaRef}
                        value={message}
                        onChange={(e) =>{
                            setMessage(e.target.value);
                        }}
                        onKeyDown={submitMessage} // 엔터치면 전송
                    ></textarea>
                    <button onClick={sendMessage}>전송</button>
                </div>

            </div>
        </div>
    )
}