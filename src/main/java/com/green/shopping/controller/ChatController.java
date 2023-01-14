package com.green.shopping.controller;

import com.green.shopping.Doc.ChatDoc;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Objects;

@RestController
@Slf4j
public class ChatController {

//    @MessageMapping("/topic")
//    public void publishChat(String chatMessage) {
//        log.info("publishChat : {}", chatMessage);
//    }

    @MessageMapping("/topic")
    @SendTo("/topic/user")
    public String sendMessage(String message){
        log.info("message : {}",message);
        return "하이";
    }

    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        log.info("[connect] connections : {}", sessionId);
    }

    @EventListener(SessionDisconnectEvent.class)
    public void onDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        log.info("[disconnect] connections : {}", sessionId);
    }
}
