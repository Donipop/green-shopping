package com.green.shopping.controller;

import com.green.shopping.Doc.ChatDoc;
import com.green.shopping.dao.ChatMongoDbDao;
import com.green.shopping.dao.impl.TalkDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final List<String> sessionIdList = new ArrayList<>();
    private final Map<String, List<Map>> chatListMap = new HashMap<>();
    private final ChatMongoDbDao chatMongoDbDao;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final TalkDaoImpl talkDaoImpl;
    public ChatController(SimpMessagingTemplate simpMessagingTemplate, ChatMongoDbDao chatMongoDbDao, TalkDaoImpl talkDaoImpl) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatMongoDbDao = chatMongoDbDao;
        this.talkDaoImpl = talkDaoImpl;
    }

    @MessageMapping("/user")
    @SendToUser("/user/queue/message")
    public void sendMessage(Map message, SimpMessageHeaderAccessor messageHeaderAccessor) {
        //접속 메세지를 받았기에 이전 채팅 내역을 보내준다
        log.info("connectMessage : {}", message);
        log.info("sessionId : {}", messageHeaderAccessor.getSessionId());
        List<Map> chatDocList = chatListMap.get(message.get("uuid").toString());
        Map<String,Object> sendMessageMap = new HashMap<>();
        sendMessageMap.put("type","connect");
        sendMessageMap.put("chatList",chatDocList);

        simpMessagingTemplate.convertAndSendToUser(messageHeaderAccessor.getSessionId(), "/queue/message",sendMessageMap,createHeaders(messageHeaderAccessor.getSessionId()));
    }

    @MessageMapping("/queue")
    @SendTo("/queue/user")
    public void sendMessageToUser(Map message, SimpMessageHeaderAccessor messageHeaderAccessor){
        //세션에 등록된 아이디
        if(sessionIdList.contains(messageHeaderAccessor.getSessionId())) {
            log.info("messageToUser : {}", message);
//            log.info("messageHeaderAccessor : {}", messageHeaderAccessor);
//            log.info("sessionId : {}", messageHeaderAccessor.getSessionId());
//            log.info(message.get("uuid").toString());
            simpMessagingTemplate.convertAndSend("/queue/user/" + message.get("uuid").toString(), message);
            String uuid = message.get("uuid").toString();
            Map<String, String> mongoDbDataMap = new HashMap<>();
            mongoDbDataMap.put("sender", message.get("userId").toString());
            mongoDbDataMap.put("message", message.get("message").toString());
            mongoDbDataMap.put("time", new Date().toString());
            chatListMap.get(uuid).add(mongoDbDataMap);
            chatMongoDbDao.save(new ChatDoc(uuid, chatListMap.get(uuid)));
//            log.info("chatListMap : {}", chatListMap);
        }

    }

    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        Map<String, Objects> headerMap = (Map<String, Objects>) event.getMessage().getHeaders().get("nativeHeaders");
        String userId = String.valueOf(headerMap.get("userId")).replaceAll("[\\[\\]]","");
        String marketOwner = String.valueOf(headerMap.get("marketOwner")).replaceAll("[\\[\\]]","");
        String uuid = String.valueOf(headerMap.get("uuid")).replaceAll("[\\[\\]]","");
        log.info("[connect] connections : {} ", headerMap);

        //로그인 되어 있다면
        if(true){
            //유저가 로그인 되어있는지 확인
            log.info("로그인 되어있음 sessionId : {}",sessionId);
            sessionIdList.add(sessionId);
            //정상적인 주소로 접근 했는지 확인

            //DB에 자료가 이전 채팅내역이 있는지 확인하는 과정
            Optional<Object> result = Optional.of(chatMongoDbDao.findById(uuid));
            if(!result.get().equals(Optional.empty())) {
                Optional<ChatDoc> chatDoc = (Optional<ChatDoc>) result.get();
                chatListMap.put(uuid, chatDoc.get().getMessageList());
                log.info("chatListMap => {} 업데이트", uuid);
//                log.info("result : {}", chatDoc.get().getMessageList());
            }else{
                log.info("DB에 {}에 대한 채팅내역이 없습니다.",uuid);
                chatListMap.put(uuid, new ArrayList<>());
            }
        }else{
            //로그인 되어있지 않다면
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
