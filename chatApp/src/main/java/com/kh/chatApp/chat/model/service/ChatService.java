package com.kh.chatApp.chat.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.chatApp.chat.model.dao.ChatDao;
import com.kh.chatApp.chat.model.vo.ChatRoom;
import com.kh.chatApp.chat.model.vo.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	
	private final ChatDao dao;

	public List<Member> selectAllUser() {
		return dao.selectAllUser();
	}

	public List<ChatRoom> selectChatRooms() {
		return dao.selectChatRooms();
	}

	public int openChatRoom(ChatRoom cr) {
		return dao.openChatRoom(cr);
	}

}
