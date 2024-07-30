package com.kh.chatApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**") // 허용할 mapping url
				.allowedMethods("*") // 허용할 method (get , post ....)
				.allowedOrigins("http://localhost:3000"); // 허용할 요청이 들어올 주소
	}

}
