package com.green.shopping.controller;

import com.green.shopping.Doc.ChatDoc;
import com.green.shopping.dao.ChatMongoDbDao;
import com.green.shopping.dao.TalkDao;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;

@RestController
@Slf4j
public class ChatController {
    private final List<String> sessionIdList = new ArrayList<>();
    private final Map<String,List<String>> sessionListMap = new HashMap<>();
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
        log.info("marketOnwer : {}",marketOwner);
        if(marketOwner.equals("")){
            log.info("marketOwner가 없음");
            marketOwner = talkDaoImpl.getMarketOwnerByUuid(uuid);
            sendMessageMap.put("type", "marketOwner");
            sendMessageMap.put("marketOwner", marketOwner);
            simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/message",sendMessageMap,createHeaders(sessionId));
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
                //채팅내용 가져옴
                List<Map> chatDocList = chatListMap.get(message.get("uuid").toString());
                sendMessageMap.put("type","connect");
                sendMessageMap.put("chatList",chatDocList);
                simpMessagingTemplate.convertAndSendToUser(messageHeaderAccessor.getSessionId(), "/queue/message",sendMessageMap,createHeaders(messageHeaderAccessor.getSessionId()));
//                log.info("result : {}", chatDoc.get().getMessageList());
            }else{
                log.info("DB에 {}에 대한 채팅내역이 없습니다.",uuid);
                chatListMap.put(uuid, new ArrayList<>());
            }

        }

    }

    @MessageMapping("/queue")
    @SendTo("/queue/user")
    public void sendMessageToUser(Map message, SimpMessageHeaderAccessor messageHeaderAccessor){
        //세션에 등록된 아이디
        if(sessionIdList.contains(messageHeaderAccessor.getSessionId())) {
            log.info("messageToUser : {}", message);
            simpMessagingTemplate.convertAndSend("/queue/user/" + message.get("uuid").toString(), message);
            String uuid = message.get("uuid").toString();
            String userId = message.get("userId").toString();
            String marketOwner = message.get("marketOwner").toString();
            Map<String, String> mongoDbDataMap = new HashMap<>();
            mongoDbDataMap.put("sender", userId);
            mongoDbDataMap.put("message", message.get("message").toString());
            mongoDbDataMap.put("time", new Date().toString());
            chatListMap.get(uuid).add(mongoDbDataMap);
            //mongoDB에 메세지 저장
            chatMongoDbDao.save(new ChatDoc(uuid, chatListMap.get(uuid)));
            //oracleDB에 카운트 저장
            messageCount(uuid,userId,marketOwner);
        }

    }

    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        Map<String, Objects> headerMap = (Map<String, Objects>) event.getMessage().getHeaders().get("nativeHeaders");
        String userId = String.valueOf(headerMap.get("userId")).replaceAll("[\\[\\]]","");
        String uuid = String.valueOf(headerMap.get("uuid")).replaceAll("[\\[\\]]","");
        String marketOwner = talkDaoImpl.getMarketOwnerByUuid(uuid);
        String loginCheck = String.valueOf(headerMap.get("loginCheck")).replaceAll("[\\[\\]]","");
        log.info("sessionId : {} , userId : {} , uuid : {} , marketOwner : {} , loginCheck : {}",sessionId,userId,uuid,marketOwner,loginCheck);
        log.info("[connect] connections : {} ", headerMap);

        Map<String,Object> sendMessageMap = new HashMap<>();
        sendMessageMap.put("type","error");
        //로그인 되어 있다면
        if(loginCheck.equals("true")) {
            //유저가 로그인 되어있는지 확인
            log.info("로그인 되어있음 sessionId : {}",sessionId);
            sessionIdList.add(sessionId);
            List<String> seList = new ArrayList<>();
            if(sessionListMap.get(uuid) == null){
                seList.add(sessionId);
                sessionListMap.put(uuid,seList);
            }else{
                sessionListMap.get(uuid).add(sessionId);
            }
            //접속했을때 카운트를 최신 카운트로 바꾼다
            if(userId.equals(marketOwner)){
                talkDaoImpl.updateMarketOwnerCountToCurrent(uuid);
                log.info("마켓 오너의 카운터를 최신 카운트로 업데이트");
            }else{
                talkDaoImpl.updateUserCountToCurrent(uuid);
                log.info("유저의 카운터를 최신 카운트로 업데이트");
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
        for(String key : sessionListMap.keySet()){
            if(sessionListMap.get(key).contains(sessionId)){
                sessionListMap.get(key).remove(sessionId);
            }
        }
    }
    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
    private void messageCount(String uuid,String userId, String marketOwner){
        int listSize =sessionListMap.get(uuid).size();
        if(userId.equals(marketOwner)){
            //마켓오너 일때
            //소켓에 접속한 세션이 두개이상 일때
            if(listSize > 1){
                talkDaoImpl.updateBothCount(uuid);
                log.info("마켓오너 메세지 이고 유저가 두명이상 접속중");
            }else if(listSize == 1){
                talkDaoImpl.updateMarketOwnerCount(uuid);
                log.info("마켓오너 메세지 이고 유저가 한명 접속중");
            }
        }else{
            //유저 일때
            //소켓에 접속한 세션이 두개이상 일때
            if(listSize > 1){
                talkDaoImpl.updateBothCount(uuid);
                log.info("유저 메세지 이고 유저가 두명이상 접속중");
            }else if(listSize == 1){
                talkDaoImpl.updateUserIdCount(uuid);
                log.info("유저 메세지 이고 유저가 한명 접속중");
            }
        }
    }
    /*==================================================================*/
    /*==========================  채팅방 API  ==============================*/
    /*==================================================================*/
    @GetMapping("/chat/getChatList")
    public List<Map<String,Object>> getChatList(@RequestParam String marketOwner){
        //마켓오너가 가지고 있는 채팅방 리스트를 가져온다
        List<String> chatList = talkDaoImpl.getIdByMarketOwner(marketOwner);
        List<Map<String,Object>> chatListMap = new ArrayList<>();
        for(String uuid : chatList){
            Map<String,Object> chatMap = new HashMap<>();
            chatMap.put("uuid",uuid);
            List<? extends Object> mongoMessageList = chatMongoDbDao.findById(uuid).get().getMessageList();
            chatMap.put("chatList",mongoMessageList.get(mongoMessageList.size()-1));
            chatMap.put("count", talkDaoImpl.getMarketOwnerCountByUuid(uuid));
            chatListMap.add(chatMap);
        }
        return chatListMap;
    }
}
