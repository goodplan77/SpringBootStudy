package com.kh.api.auth.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.kh.api.auth.filter.AuthFilter;
import com.kh.api.auth.jwt.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
	
	private final JwtProvider jwtProvider;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		//
		http.cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
			// cors 방어 설정
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // 허용할 origin
				config.setAllowedMethods(Collections.singletonList("*")); // 허용할 method
				config.setAllowCredentials(true); // jwt 사용시에만 이용가능
				config.setAllowedHeaders(Collections.singletonList("*")); // 요청 헤더
				config.setMaxAge(3600L); // 캐싱 시간 설정 (1시간)
				return config;
			}
			
		}))
		.csrf((csrfConfig) -> csrfConfig.disable()) // CSRF 방어 설정 - 비활성화
		.sessionManagement(config ->
			config.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 설정 비 활성화
		)
		.authorizeHttpRequests((authorizeRequest) -> authorizeRequest //url 별 권한 관리
				.requestMatchers("/auth/login/**").permitAll() // 누구나 이용가능한 url
				.requestMatchers("/**").hasRole("USER") // 그외는 user 권한이 필요
				//.requestMatchers("/admin/**").hasRole("ADMIN") 관리자 권한용?
				.anyRequest().authenticated()
				)
		// 필터 설정 사용자 이름과 비밀번호 인증 전에 토큰 발행 필터?
		.addFilterBefore(new AuthFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

}
