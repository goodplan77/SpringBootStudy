package com.kh.api.auth.model.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.api.auth.model.dao.AuthDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService{

	private final AuthDao dao;
	
	@Override
	public UserDetails loadUserByUsername(String socialId) throws UsernameNotFoundException {
		return dao.loadUserByUsername(Long.parseLong(socialId), "kakao");
	}

}
