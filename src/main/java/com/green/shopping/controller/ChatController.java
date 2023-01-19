package com.green.shopping.controller;

import com.green.shopping.Doc.ChatDoc;
import com.green.shopping.dao.ChatMongoDbDao;
import com.green.shopping.dao.impl.TalkDaoImpl;
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
        String sessionId = messageHeaderAccessor.getSessionId();
        String userId = message.get("userId").toString();
        String marketOwner = message.get("marketOwner").toString();
        String uuid = message.get("uuid").toString();

        List<Map> chatDocList = chatListMap.get(message.get("uuid").toString());
        Map<String,Object> sendMessageMap = new HashMap<>();
        sendMessageMap.put("type","error");
        //정상적인 주소로 접근 했는지 확인
        if(userId.equals(null) && marketOwner.equals(null)){
            log.info("정상적인 접근이 아님");
            //Error 메세지 전송
            sendMessageMap.put("code","1");
            simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/message",sendMessageMap,createHeaders(sessionId));
            return;
        }
        if(userId.equals(marketOwner)) {
            //유저아이디랑 마켓오너가 같을때 uuid유효성 검사
            log.info("유저아이디랑 마켓오너가 같음");
            Map<String, Object> selectMap = talkDaoImpl.select(uuid);
            log.info("selectMap : {}", selectMap);
            if(selectMap == null) {
                //Error 메세지 전송
                sendMessageMap.put("code","4");
                simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/message",sendMessageMap,createHeaders(sessionId));
                return;
            }
            if (selectMap.get("MARKETOWNER").equals(marketOwner)) {
                //정상적인 접근
                log.info("마켓오너 정상적인 접근");
                sendMessageMap.put("type", "connect");
            }
        }
        Optional<String> uuidFromDB = Optional.ofNullable(talkDaoImpl.getIdByUserIdAndMarketOwner(userId, marketOwner));
        //채팅방이 존재하는지 확인
        if(uuidFromDB.equals(Optional.empty()) && sendMessageMap.get("type").equals("error")){
            log.info("채팅방이 존재하지 않음");
            //채팅방을 만드는 과정
            String _uuid = UUID.randomUUID().toString();
            talkDaoImpl.insert(_uuid,userId,marketOwner);
            log.info("{} 채팅방 생성 완료",_uuid);
            sendMessageMap.put("code","3");
            sendMessageMap.put("uuid",_uuid);
            //생성된 uuid를 보내서 다시 연결 시킨다
            simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/message",sendMessageMap,createHeaders(sessionId));
            return;
        }else{
            if(sendMessageMap.get("type").equals("error")) {
                String _uidFromDB = uuidFromDB.toString().replaceAll("Optional", "").replaceAll("[\\[\\]]", "");
                //헤더에 달려있는 uuid와 db에 저장되어 있는 uuid의 값을 비교
                if (!uuid.equals(_uidFromDB)) {
                    sendMessageMap.put("code", "2");
                    sendMessageMap.put("uuid", _uidFromDB);
                    simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/message", sendMessageMap, createHeaders(sessionId));
                    log.info("{} 잘못된접근 uuid 다름 {}", uuid, _uidFromDB);
                    return;
                }
            }
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
            sendMessageMap.put("type","connect");
            sendMessageMap.put("chatList",chatDocList);
            simpMessagingTemplate.convertAndSendToUser(messageHeaderAccessor.getSessionId(), "/queue/message",sendMessageMap,createHeaders(messageHeaderAccessor.getSessionId()));

        }

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

        Map<String,Object> sendMessageMap = new HashMap<>();
        sendMessageMap.put("type","error");

        //로그인 되어 있다면
        if(true){
            //유저가 로그인 되어있는지 확인
            log.info("로그인 되어있음 sessionId : {}",sessionId);
            sessionIdList.add(sessionId);

//            //정상적인 주소로 접근 했는지 확인
//            if(userId.equals(null) && marketOwner.equals(null)){
//                log.info("정상적인 접근이 아님");
//                //Error 메세지 전송
//                sendMessageMap.put("code","1");
//                simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/message",sendMessageMap,createHeaders(sessionId));
//                return;
//            }
//            Optional<String> uuidFromDB = Optional.ofNullable(talkDaoImpl.getIdByUserIdAndMarketOwner(userId, marketOwner));
//            //채팅방이 존재하는지 확인
//            if(uuidFromDB.equals(Optional.empty())){
//                log.info("채팅방이 존재하지 않음");
//                //채팅방을 만드는 과정
//                String _uuid = UUID.randomUUID().toString();
//                talkDaoImpl.insert(_uuid,userId,marketOwner);
//                log.info("{} 채팅방 생성 완료",_uuid);
//                sendMessageMap.put("code","3");
//                sendMessageMap.put("uuid",_uuid);
//                //생성된 uuid를 보내서 다시 연결 시킨다
//                simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/message",sendMessageMap,createHeaders(sessionId));
//                return;
//            }else{
//                //헤더에 달려있는 uuid와 db에 저장되어 있는 uuid의 값을 비교
//                if(uuid != uuidFromDB.toString()){
//                    sendMessageMap.put("code","2");
//                    simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/message",sendMessageMap,createHeaders(sessionId));
//                    log.info("잘못된접근 uuid 다름");
//                    return;
//                }
//            }

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
