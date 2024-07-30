import axios from "axios";
import { useEffect, useState } from "react"
import { ChatRoom } from "../type/type";
import { useSelector } from "react-redux";
import { RootState } from "../store/store";
import { useNavigate } from "react-router-dom";

export default function ChattingRoomList() {
    
    let [chatRoomList , setChatRoomList] = useState<ChatRoom[]>([]);
    let [modal , setModal] = useState(false);
    const navi = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8083/chatApp/chatRoomList")
            .then((response) => {
                setChatRoomList(response.data);
            })
    },[]);

    return (
        <>
            <section className="board-list">
                <h1 className="board-name">채팅방 목록</h1>
                <div className="list-wrapper">
                    <table className="list-table">
                        <thead>
                            <tr>
                                <th>방번호</th>
                                <th>채팅방 주제(제목)</th>
                                <th>개설자</th>
                                <th>참여인원수</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                chatRoomList.length == 0 ? (
                                    <tr>
                                        <td colSpan={4}>채팅방이 없습니다.</td>
                                    </tr>
                                ) : (
                                    chatRoomList.map((chatRoom) => {
                                        return (
                                            <tr key={chatRoom.chatRoomNo}>
                                                <td>{chatRoom.chatRoomNo}</td>
                                                <td>{chatRoom.title}
                                                    <button onClick={() => navi("/detail/"+chatRoom.chatRoomNo)}>참여</button>
                                                </td>
                                                <td>{chatRoom.nickName}</td>
                                                <td>{chatRoom.cnt}</td>
                                            </tr>
                                        )
                                    })
                                )
                            }
                        </tbody>
                    </table>
                    <div className="btn-area">
                        <button onClick={() => setModal(true)}>채팅방만들기</button>
                    </div>
                </div>
            </section>
            {modal && <ChatModal setModal={setModal} />}
        </>
    )
}

function ChatModal(props:{setModal:(b:boolean)=>void}) {
    const {setModal} = props;
    const [title , setTitle] = useState('');
    const user = useSelector((state:RootState) => state.user);
    const navi = useNavigate();

    const openChatRoom = () => {
        axios
            .post("http://localhost:8083/chatApp/openChatRoom" , {
                title ,
                userNo : user.userNo
            })
            .then(response => {
                console.log(response);
                navi('/detail/'+response.data);
            })
    }
    
    return (
        <div className="modal">
            <div className="modal-content">
                <span className="close" onClick={() => setModal(false)}>&times;</span>
                <div className="login-form">
                    <h3>채팅방 만들기</h3>
                    <input type="text" name="title" value={title}
                    onChange={(e) => { setTitle(e.target.value)}}
                    className="form-control" placeholder="채팅방 제목"
                    /><button onClick={openChatRoom}>만들기</button>
                </div>
            </div>
        </div>
    )
}