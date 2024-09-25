package com.src.Chat.config;

import com.src.Chat.socket.ChatMessage;
import com.src.Chat.socket.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event // Đại diện cho sự kiện ngắt kết nối của một phiên WebSocket, chứa thông tin về client đã ngắt kết nối
    ) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if(username != null) {
            log.info("User disconnected: {}", username); //Ghi lại thông tin ngắt kết nối của người dùng vào log

            /*
                Đối tượng này được cấu hình với loại tin nhắn là LEAVE và tên người gửi là username,
                đại diện cho việc người dùng đã rời khỏi cuộc trò chuyện.
            */
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();

            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
