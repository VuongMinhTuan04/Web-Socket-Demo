package com.src.Chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //Kích hoạt Web Socket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer { //Triển khai giao diện Web Socket
    //Ctrl + O
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
        /*
            Đăng ký endpoint WebSocket tại "/ws" và kích hoạt SockJS cho khả năng fallback.
        */
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        /*
            Đặt tiền tố cho các địa chỉ đích của ứng dụng là "/app"
        */

        registry.enableSimpleBroker("/topic");
        /*
            Kích hoạt broker đơn giản cho các chủ đề bắt đầu bằng "/topic"
        */
    }
}
