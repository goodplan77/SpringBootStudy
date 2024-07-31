package com.kh.chatApp.chat.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.chatApp.chat.model.vo.ChatMessage;
import com.kh.chatApp.chat.model.vo.ChatRoom;
import com.kh.chatApp.chat.model.vo.ChatRoomJoin;
import com.kh.chatApp.chat.model.vo.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatDao {
	
	private final SqlSessionTemplate session;

	public List<Member> selectAllUser() {
		return session.selectList("chat.selectAllUser");
	}

	public List<ChatRoom> selectChatRooms() {
		return session.selectList("chat.selectChatRooms");
	}

	public int openChatRoom(ChatRoom cr) {
		return session.insert("chat.openChatRoom" , cr);
	}

	public void insertChatMessage(ChatMessage chatMessage) {
		session.insert("chat.insertChatMessage" , chatMessage);
	}

	public void joinChatRoom(ChatRoomJoin crj) {
		session.insert("chat.joinChatRoom" , crj);
	}

	public Member selectUser(ChatRoomJoin crj) {
		return session.selectOne("chat.selectUser" , crj);
	}

	public List<ChatMessage> selectMessages(int chatRoomNo) {
		return session.selectList("chat.selectMessages" , chatRoomNo);
	}

	public ChatMessage selectChatMessage(int cmNo) {
		return session.selectOne("chat.selectChatMessage" , cmNo);
	}

	public List<Member> selectChatRoomMembers(int chatRoomNo) {
		return session.selectList("chat.selectChatRoomMembers" , chatRoomNo);
	}

	public void exitMember(ChatRoomJoin crj) {
		session.delete("chat.exitMember" , crj);
	}

	public void updateUserStatus(ChatRoomJoin crj) {
		session.update("chat.updateUserStatus" , crj);
	}

}
