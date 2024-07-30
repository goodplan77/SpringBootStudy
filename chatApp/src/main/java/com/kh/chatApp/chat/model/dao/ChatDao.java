package com.kh.chatApp.chat.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.chatApp.chat.model.vo.ChatRoom;
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

}
