package com.kh.api.auth.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.api.auth.jwt.JwtProvider;
import com.kh.api.auth.model.dto.User;
import com.kh.api.auth.model.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	
	private final AuthService authService;
	private final JwtProvider jwtProvider;
	
	// @CrossOrigin(origins = {"http://localhost:3000"})
	@PostMapping("/login/{socialType}")
	public ResponseEntity<HashMap<String , Object>> authCheck(
			@PathVariable String socialType,
			@RequestBody HashMap<String , String> param
			){
		// 1) 클라이언트로부터 전달받은 accessToken을 활용하여 회원가입진행
		// 카카오 서버로 accessToken 을 보낸 후 user 정보 얻어오기.
		User user = authService.login(param.get("accessToken"));
		
		HashMap<String , Object> map = new HashMap<>();
		
		// 2) 유저의 PK 값을 통해 서버용 jwt 토큰을 생성해서 클라이언트에게 반환
		String ACCESS_TOKEN = jwtProvider.createToken(user.getSocialId());
		map.put("jwtToken", ACCESS_TOKEN);
		map.put("user" , user);
		
		return ResponseEntity.ok(map);
	}

}
