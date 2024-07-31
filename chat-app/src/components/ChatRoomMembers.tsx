import { Member } from "../type/type";

export default function ChatRoomMembers({chatRoomMembers}:{chatRoomMembers:Member[]}){
    return(
        <div className="chat-room-members">
            <h4>참여자 목록</h4>
            <ul className="chat-room-members-ul">
                {
                    chatRoomMembers.map( member => {
                        return(
                            <li key={member.userNo}>
                                <span className={"user-status "+(member.userStatus == 1 ? 'online' : 'offline' )}></span>{member.nickName}
                            </li>
                        )
                    })    
                }
            </ul>
        </div>
    )
}