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
    profile : string
}

export const initMember:Member = {
    userNo : 0 ,
    nickName : '' ,
    profile : ''
}