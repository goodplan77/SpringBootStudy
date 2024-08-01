package com.kh.api.auth.model.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
	private long userNo;
	private String nickName;
	private String enrollDate;
	private String profile;
	
	private String socialId;
	private String socialType;
	
	// 복수개의 권한을 저장할 필드
	// SimpleGrantedAuthority : 문자열로 이루어진 권한
	private List<SimpleGrantedAuthority> authorities;

	public Collection<? extends GrantedAuthority> getAuthorities(){
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}
}
