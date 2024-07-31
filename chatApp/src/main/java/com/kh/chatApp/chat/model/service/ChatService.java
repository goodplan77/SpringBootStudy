package com.kh.chatApp.chat.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.chatApp.chat.model.dao.ChatDao;
import com.kh.chatApp.chat.model.vo.ChatMessage;
import com.kh.chatApp.chat.model.vo.ChatRoom;
import com.kh.chatApp.chat.model.vo.ChatRoomJoin;
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

	public ChatMessage insertChatMessage(ChatMessage chatMessage) {
		dao.insertChatMessage(chatMessage);
		return dao.selectChatMessage(chatMessage.getCmNo());
	}

	public void joinChatRoom(ChatRoomJoin crj) {
		try {			
			dao.joinChatRoom(crj);
		}catch(Exception e) {
			// e.printStackTrace();
		}
	}

	public Member selectUser(ChatRoomJoin crj) {
		return dao.selectUser(crj);
	}

	public List<ChatMessage> selectMessages(int chatRoomNo) {
		return dao.selectMessages(chatRoomNo);
	}

	public List<Member> selectChatRoomMembers(int chatRoomNo) {
		return dao.selectChatRoomMembers(chatRoomNo);
	}

	public void exitMember(ChatRoomJoin crj) {
		dao.exitMember(crj);
	}

	public void updateUserStatus(ChatRoomJoin crj) {
		dao.updateUserStatus(crj);
	}

}
