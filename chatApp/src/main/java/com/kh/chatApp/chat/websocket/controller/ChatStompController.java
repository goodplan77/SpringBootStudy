package com.kh.chatApp.chat.websocket.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.kh.chatApp.chat.model.service.ChatService;
import com.kh.chatApp.chat.model.vo.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatStompController {
	
	private final ChatService service;
	
	@MessageMapping("/chat/sendMessage/chatRoomNo/{chatRoomNo}")
	public ChatMessage insertMessage(
			@DestinationVariable int chatRoomNo,
			ChatMessage chatMessage // argument resolver 에 의해 알아서 넘겨짐?
			) {
		log.info("chatRoomNo ?? {}" , chatRoomNo);
		log.info("chatMessage ?? {}" , chatMessage);
		return null;
	}

}
