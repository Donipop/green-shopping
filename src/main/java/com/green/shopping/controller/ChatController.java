package com.green.shopping.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;

@RestController
@Slf4j
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final List<String> sessionIdList = new ArrayList<>();
    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/topic/")
    @SendTo("/topic/user")
    public String sendMessage(String message){
        log.info("message : {}",message);
        return "하이";
    }

    @MessageMapping("/queue")
    @SendToUser("/queue/user")
    public void sendMessageToUser(Map message, SimpMessageHeaderAccessor messageHeaderAccessor){

        if(sessionIdList.contains(messageHeaderAccessor.getSessionId())){
            log.info("messageToUser : {}",message);
            log.info("messageHeaderAccessor : {}",messageHeaderAccessor);
            log.info("sessionId : {}",messageHeaderAccessor.getSessionId());
            log.info(message.get("uuid").toString());
            simpMessagingTemplate.convertAndSend("/queue/user/" + message.get("uuid").toString() ,message);
        }else{
            simpMessagingTemplate.convertAndSend("/queue/user/" + message.get("uuid").toString() ,"로그인이 필요합니다.");
        }

    }

    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        Map<String, Objects> headerMap = (Map<String, Objects>) event.getMessage().getHeaders().get("nativeHeaders");
        String userId = String.valueOf(headerMap.get("userId")).replaceAll("[\\[\\]]","");
        String marketOwner = String.valueOf(headerMap.get("marketOwner")).replaceAll("[\\[\\]]","");
        String uuid = String.valueOf(headerMap.get("uuid")).replaceAll("[\\[\\]]","");
        log.info("userID : {}",userId);
        log.info("marketOwner: {}",marketOwner);
        log.info("uuid : {}", uuid);
        log.info("[connect] connections : {}", sessionId);



        //로그인 되어 있다면 리스트에 추가
        if(uuid.equals("123")){
            //유저가 로그인 되어있는지 확인
            log.info("로그인 ok");
            sessionIdList.add(sessionId);
            log.info("sessionIdList : {}",sessionIdList);
        }else{
            log.info("로그인 no");
        }


    }

    @EventListener(SessionDisconnectEvent.class)
    public void onDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        log.info("[disconnect] connections : {}", sessionId);
        //접속 끊기면 삭제
        sessionIdList.remove(sessionId);
    }
    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}
