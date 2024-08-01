package com.kh.api.auth.model.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.api.auth.model.dto.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthDao {
	
	private final SqlSessionTemplate session;

	public User loadUserByUsername(Long socialId, String string) {
		Map<String , Object> param = new HashMap<>();
		param.put("socialId", socialId);
		param.put("socialType", string);
		return session.selectOne("auth.loadUserByUsername" , param);
	}

	public void insertUser(User m) {
		session.insert("auth.insertUser" , m);
	}

	public void insertUserSocial(User m) {
		session.insert("auth.insertUserSocial" , m);
	}

	public void insertAuthority(User m) {
		session.insert("auth.insertAuthority" , m);
	}

}
