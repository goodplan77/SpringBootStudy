package com.kh.api.auth.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.kh.api.auth.jwt.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthFilter extends GenericFilterBean{
	
	private final JwtProvider jwtProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 클라이언트의 api 요청 헤더에서 토큰을 추출
		String token = jwtProvider.resolveToken((HttpServletRequest) request);
		
		// 토큰 유효성 검사
		// 사용 할 수 있는 토큰이라면 , 사용자 인증정보 조회 후 Security Context에 저장
		if(token != null && jwtProvider.validationToken(token)) {
			//Authentication 객체 생성 (사용자 인가 정보)
			Authentication authentication = jwtProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		chain.doFilter(request, response);
		
	}

}
