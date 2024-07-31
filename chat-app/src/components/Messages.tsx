import { useSelector } from "react-redux";
import { ChatMessage } from "../type/type";
import { RootState } from "../store/store";
import MyChat from "./MyChat";
import OtherChat from "./OtherChat";

export default function Messages({chatMessages}:{chatMessages:ChatMessage[]}){
    
    let {userNo} = useSelector((state:RootState) => state.user);
    
    return (
        <>
            {
                chatMessages.map((chat) => {
                    return(
                        chat.userNo == userNo ?
                        <MyChat chat={chat}/> : <OtherChat chat={chat}/>
                    )
                })
            }
        </>
    )
}