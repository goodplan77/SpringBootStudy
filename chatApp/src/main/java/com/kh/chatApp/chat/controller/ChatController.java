package com.kh.chatApp.chat.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.chatApp.chat.model.service.ChatService;
import com.kh.chatApp.chat.model.vo.ChatMessage;
import com.kh.chatApp.chat.model.vo.ChatRoom;
import com.kh.chatApp.chat.model.vo.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ChatController {
	private final ChatService service;
	
	@GetMapping("/allUsers")
	public List<Member> selectAllUser(){
		return service.selectAllUser();
	}
	
	@GetMapping("/chatRoomList")
	public List<ChatRoom> selectChatRooms() {
		return service.selectChatRooms();
	}
	
	@PostMapping("/openChatRoom")
	public int openChatRoom(@RequestBody ChatRoom cr) {
		service.openChatRoom(cr);
		return cr.getChatRoomNo();
	}
	
	@GetMapping("/chatMessage/chatRoomNo/{chatRoomNo}")
	public List<ChatMessage> selectMessages(
			@PathVariable int chatRoomNo
			){
		return service.selectMessages(chatRoomNo);
	}
	
	@GetMapping("/chatRoomJoin/chatRoomNo/{chatRoomNo}")
	public List<Member> selectChatRoomMembers(
			@PathVariable int chatRoomNo
			){
		return service.selectChatRoomMembers(chatRoomNo);
	}
}
