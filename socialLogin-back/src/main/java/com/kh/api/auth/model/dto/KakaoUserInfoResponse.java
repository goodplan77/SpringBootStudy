package com.kh.api.auth.model.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoUserInfoResponse {
	private Long id;
	private Boolean has_signed_up;
	
	private KakaoAccount kakao_account;
	private Properties properties;
	
	// 내부 클래스
	@Getter
	public static class KakaoAccount {
		private Boolean profile_needs_agreement;
		private Boolean profile_nickname_needs_agreement;
		private Boolean profile_image_needs_agreement;
	}
	
	@Getter
	public static class Properties {
		private String nickname;
		private String profile_image;
		private String thumbnail_image;
	}
}
