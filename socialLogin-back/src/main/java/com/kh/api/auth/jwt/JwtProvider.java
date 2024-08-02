package com.kh.api.auth.jwt;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.kh.api.auth.model.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

// JSON WEB TOKEN Provider
@Component
@RequiredArgsConstructor
public class JwtProvider {
	
	private final UserDetailsService service;
	
	// 키 설정
	@Value("${jwt.secret}")
	private String secretKey; // 평문
	
	// 초기 값
	@PostConstruct
	public void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // 암호화
	}
	
	// 현재 애플리케이션 자체 토큰 생성
	public String createToken(String userPk) {
		Claims claims = Jwts.claims().setSubject(userPk); // jwt 토큰 생성 + payload 저장
		Date now = new Date();
		return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now) // 토큰 발생시칸 설정
					.setExpiration(new Date(now.getTime() + (30*60*1000))) // 토큰 만료 시간 설정 - 30분
					.signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘 설정
					.compact();
	}

	// 응답 헤더에서 토큰 정보 받아오기
	public String resolveToken(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		
		return accessToken;
	}

	// 토큰 유효성 검사 메소드
	public boolean validationToken(String token) {
		try {
		// 받아온 토큰 정보 복호화 후 저장
		Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		
		// 토큰 유효성 검사 (비어 있는지 + 유효 기간 확인)
		return !claims.getBody().getExpiration().before(new Date());
		} catch(Exception e) {
			return false;
		}
	}

	/*
	 * Authentication : 사용자 인증 정보가 담겨 있는 객체
	 * - Principal 	 : 	인증된 사용자 정보
	 * - Credentials :	인증에 필요한 비밀번호를 저장하는 객체
	 * 					내부적으로 인증작업시 필요하며 , 보호되고 있음.
	 * - Authorities : 	인증된 사용자가 가진 권한 목록
	 */
	public Authentication getAuthentication(String token) {
			User user = (User) service.loadUserByUsername(getUserPk(token));
			
			// 정보 , 비밀번호 , 권한 목록을 통해
			// Authentication 객체 생성
			return new UsernamePasswordAuthenticationToken(user, "" , user.getAuthorities());
	}
	
	// 토큰에서 userPk값을 꺼내는 메서드
	public String getUserPk(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
}
