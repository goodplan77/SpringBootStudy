package com.kh.api.auth.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.api.auth.model.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	
	private final AuthService authService;
	
	// @CrossOrigin(origins = {"http://localhost:3000"})
	@PostMapping("/login/{socialType}")
	public ResponseEntity<HashMap<String , Object>> authCheck(
			@PathVariable String socialType,
			@RequestBody String accessToken
			){
		log.info("socialType  = {} , accessToken = {}");
		// 1) 클라이언트로부터 전달받은 accessToken을 활용하여 회원가입진행
		// 카카오 서버로 accessToken 을 보낸 후 user 정보 얻어오기.
		authService.login(accessToken);
		
		return null;
	}

}
