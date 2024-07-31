export type ChatRoom = {
    chatRoomNo:number,
    title:string,
    userNo:number
    nickName : string,
    cnt : number
}

export type Member = {
    userNo : number ,
    nickName : string ,
    profile : string ,
    userStatus? : 1|2
}

export const initMember:Member = {
    userNo : 0 ,
    nickName : '' ,
    profile : ''
}

export type ChatMessage = {
    message : string ,
    createDate : string ,
    userNo : number ,
    nickName : string ,
    profile : string
}