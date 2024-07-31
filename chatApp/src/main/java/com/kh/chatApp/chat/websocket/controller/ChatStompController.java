package com.kh.chatApp.chat.websocket.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.kh.chatApp.chat.model.service.ChatService;
import com.kh.chatApp.chat.model.vo.ChatMessage;
import com.kh.chatApp.chat.model.vo.ChatRoomJoin;
import com.kh.chatApp.chat.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatStompController {
	
	private final ChatService service;
	
	@MessageMapping("/sendMessage/chatRoomNo/{chatRoomNo}")
	@SendTo("/chat/chatRoomNo/{chatRoomNo}/message") // 해당하는 url로 발행
	public ChatMessage insertMessage(
			@DestinationVariable int chatRoomNo,
			ChatMessage chatMessage // argument resolver 에 의해 알아서 넘겨짐?
			) {
		log.info("chatRoomNo ?? {}" , chatRoomNo);
		log.info("chatMessage ?? {}" , chatMessage);
		
		// 1) db에 채팅 메세지 등록
		return service.insertChatMessage(chatMessage);
		
		// 2) 같은방 사용자에게 채팅내용 전달
		//return chatMessage;
	}
	
	@MessageMapping("/chatRoomJoin/{chatRoomNo}/{userNo}/newMember")
	@SendTo("/chat/chatRoomNo/{chatRoomNo}/newMember")
	public Member newMember(
			@DestinationVariable int chatRoomNo,
			@DestinationVariable int userNo,
			ChatRoomJoin crj,
			Member m
			) {
		crj.setChatRoomNo(chatRoomNo);
		crj.setUserNo(userNo);
		log.info("crj = {}" , crj);
		
		// 1) ChatRoomJoin 에 데이터 추가
		service.joinChatRoom(crj);
		
		// 2) 참여한 회원정보 조회
		m = service.selectUser(crj);
		
		return m;
		
	}
	
	@MessageMapping("/chatRoomJoin/{chatRoomNo}/{userNo}/delete")
	@SendTo("/chat/chatRoomNo/{chatRoomNo}/exitMember")
	public Member exitMember(
			@DestinationVariable int chatRoomNo,
			@DestinationVariable int userNo,
			@RequestBody Member m
			) {
		// ChatRoomJoin 테이블에서 데이터 제거
		ChatRoomJoin crj = new ChatRoomJoin();
		crj.setChatRoomNo(chatRoomNo);
		crj.setUserNo(userNo);
		
		service.exitMember(crj);
		
		// 나가기로한 회원정보 반환
		return m;
	}
	
	@MessageMapping("/chatRoomJoin/chatRoomNo/{chatRoomNo}/updateStatus")
	@SendTo("/chat/chatRoomNo/{chatRoomNo}/updateStatus")
	public Member updateStatus(
			@DestinationVariable int chatRoomNo , 
			@RequestBody Member m
			) {
		log.info("member : {}" , m);
		ChatRoomJoin crj = new ChatRoomJoin();
		crj.setUserNo(m.getUserNo());
		crj.setChatRoomNo(chatRoomNo);
		crj.setUserStatus(m.getUserStatus());
		service.updateUserStatus(crj);
		return m;
	}
	
}
