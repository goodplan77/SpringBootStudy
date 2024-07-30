import { KeyboardEvent ,  useEffect, useRef, useState } from "react";
import ChatRoomMembers from "../components/ChatRoomMembers";
import SockJs from 'sockjs-client';
import { Client } from "@stomp/stompjs";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../store/store";

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
    // 현재 접속중인 user 정보
    const user = useSelector((state:RootState) => state.user);
    
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
                    console.log(frame);
                });
            }
        });

        // 웹소켓 활성화
        stompClient.activate(); 
        setWebSocket(stompClient);

        // 컴포넌트 소멸시 웹소켓 해제
        return () => {
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
    }

    return (
        <div className = "main">
            {/* 1. 채팅방 참여자 목록 */}
            <ChatRoomMembers/>
            <div className = "chatting-area">

                <div className="chat-header">
                    <button className="btn btn-outline-danger">나가기</button>
                </div>

                {/* 채팅내용 */}
                <ul className="display-chatting">

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