package com.example.kiemtra1.WebRocket;

import com.example.kiemtra1.DTO.MemberData;
import com.example.kiemtra1.DecodeJWT.JwtDecoder1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller

public class ChatController {
    @Autowired
    public JwtDecoder1 jwtDecoder1;

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public String sendMessage(@Payload Map<String,String>payload) {
        String message = payload.get("message");
        String token = payload.get("token");
        MemberData memberData = jwtDecoder1.decode(token);
        return message;
    }

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public Message adduser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.sender);
        return message;
    }
}
