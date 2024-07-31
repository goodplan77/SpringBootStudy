package com.kh.chatApp.chat.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
 * Stomp
 * - Stomp Text Oriented Messaging Protocol
 * - publish 발행 / subscribe 구독 패턴
 * - 특정 url을 "구독" 하고 있는 클라이언트에게 메세지를 "발행"해줌
 */

@EnableWebSocketMessageBroker // 메세지 중개자
@Configuration // bean - scanning 시 설정 정보 파일로 인식하게 함
public class Stompconfig implements WebSocketMessageBrokerConfigurer{
	
	// 서버 연결
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
		.addEndpoint("/stompServer") // endpoint url
		.setAllowedOrigins("http://localhost:3000") // 허용 origin
		.withSockJS(); // socketJs
	}
	
	// 메세지 브로커 설정
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		// 브로커 활성화
		// /'chat' 들어오는 (시작하는?) 메세지를 중계
		registry.
		enableSimpleBroker("/chat");
		
		// 들어오는 요청에 대해서 해당하는 접두어를 붙여줌
		registry.setApplicationDestinationPrefixes("/chat");
	}
}
