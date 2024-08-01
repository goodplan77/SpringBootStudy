package com.kh.api.auth.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.api.auth.model.dao.AuthDao;
import com.kh.api.auth.model.dto.KakaoUserInfoResponse;
import com.kh.api.auth.model.dto.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
	
	private final KakaoApi kakaoApi;
	private final AuthDao dao;

	@Transactional(readOnly = true , rollbackFor = Exception.class)
	public User login(String accessToken) {
		// 카카오 서버에 유저 데이터 요청
		KakaoUserInfoResponse userInfo = kakaoApi.getUserInfo(accessToken);
		log.info("userInfo ?? {}" , userInfo);
		
		Long socialId = userInfo.getId();
		
		// 현재 어플리케이션에 사용자정보가 있는지 조회
		User user = dao.loadUserByUsername(socialId , "kakao");
		
		if(user == null) {
			// 회원 가입
			String nickName = userInfo.getProperties().getNickname();
			String profile = userInfo.getProperties().getProfile_image();
			
			User m = User.builder()
					.nickName(nickName)
					.profile(profile)
					.socialId(String.valueOf(socialId))
					.socialType("kakao")
					.build();
			dao.insertUser(m);
			dao.insertUserSocial(m);
			dao.insertAuthority(m);
			
			user = dao.loadUserByUsername(socialId, "kakao");
		}
		
		return user;
	}

}
