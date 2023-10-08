package com.example.kiemtra1.WebRocket;

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
public class WebSocketEvent {
    private  final SimpMessageSendingOperations messageSendingOperations;
    @EventListener
    public void handleWebsocketDisconnet(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("user disconneted ", username);
            var message = Message.builder()
                    .type(MessageType.LEAVER)
                    .sender(username).build();
            messageSendingOperations.convertAndSend("/topic/public",message);
        }
    }
}
