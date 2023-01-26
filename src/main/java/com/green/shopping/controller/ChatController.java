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
    private final Map<String, String> sessionToUUIDListMap = new HashMap<>();
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
        //접속 메세지를 받음
        log.info("connectMessage : {}", message);
        log.info("sessionId : {}", messageHeaderAccessor.getSessionId());
        String sessionId = messageHeaderAccessor.getSessionId();
        String userId = message.get("userId").toString();
        String uuid = message.get("uuid").toString();
        String type = message.get("type").toString();
        log.info(message.toString());
        HashMap<String,Object> sendMessageMap = new HashMap<>();
        sendMessageMap.put("Type", "Connect");
        sendMessageMap.put("Message", "접속되었습니다.");
        sendMessageMap.put("UUID", sessionToUUIDListMap.get(sessionId));

        simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/message", sendMessageMap,createHeaders(messageHeaderAccessor.getSessionId()));

    }

    @MessageMapping("/queue")
    @SendTo("/queue/user")
    public void sendMessageToUser(Map message, SimpMessageHeaderAccessor messageHeaderAccessor) {
        //세션에 등록된 아이디
        if (sessionIdList.contains(messageHeaderAccessor.getSessionId())) {

        }

    }

    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event) {
//        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        Map<String, Objects> headerMap = (Map<String, Objects>) event.getMessage().getHeaders().get("nativeHeaders");
        String userId = String.valueOf(headerMap.get("userId")).replaceAll("[\\[\\]]", "");
        String marketOwner = String.valueOf(headerMap.get("marketOwner")).replaceAll("[\\[\\]]", "");
        String uuid = String.valueOf(headerMap.get("uuid")).replaceAll("[\\[\\]]", "");
        String loginCheck = String.valueOf(headerMap.get("loginCheck")).replaceAll("[\\[\\]]", "");
        String productId = String.valueOf(headerMap.get("productId")).replaceAll("[\\[\\]]", "");
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        //내가 가지고 있는 정보 userId랑 marketOwnerId
        log.info("[connect] connections : {}", sessionId);

        if (loginCheck.equals("true")) {
            //로그인 되어있는 상태
//            (String userId, String uuid, String productId, String marketOwner, String sessionId)
            checkChatRoom(userId, uuid, productId, marketOwner, sessionId);
            //이전 채팅내역 불러오기
            getBeforeChatList(sessionId);
        }

    }

    private void getBeforeChatList(String sessionId) {
        //이전 채팅내역 불러오기
        String uuid = sessionToUUIDListMap.get(sessionId);
        if(uuid == null){
            return;
        }
        Optional<Object> chatObject = Optional.ofNullable(chatMongoDbDao.findById(uuid));
        log.info("chatObject : {}", chatObject);
        if (!chatObject.get().equals(Optional.empty())) {
            Optional<ChatDoc> chatDoc = (Optional<ChatDoc>)chatObject.get();
            chatListMap.put(uuid, chatDoc.get().getMessageList());
            log.info("{} 채팅내역 불러옴", uuid);
        }else{
            log.info("{} 채팅내역 없음", uuid);
        }
    }

    private void checkChatRoom(String userId, String uuid, String productId, String marketOwner, String sessionId) {
        log.info("userID {} marketOwner {} uuid {} sessionId {} productId {}", userId, marketOwner,uuid, sessionId, productId);
        if (userId == null) {
            log.info("[잘못된 접근] userId null");
            return;
        }
        if (uuid.equals("view")) {
            if (productId == null) {
                log.info("[잘못된 접근] productId null");
                return;
            }
            //uuid => view 라면 view 페이지에서 접속한 것이므로
            //productId로 marketOwner를 구해옴
            marketOwner = talkDaoImpl.getMarketOwnerIdByProductId(Integer.parseInt(productId));
            //userId와 marketOwner를 비교
            if (userId.equals(marketOwner)) {
                //같으면 접속 불가
                log.info("[잘못된 접근] userId와 marketOwner가 같음");
                return;
            }
            //userId와 marketOwner로 uuid를 구해옴
            Optional<String> uuidFromDB = Optional.ofNullable(talkDaoImpl.getIdByUserIdAndMarketOwner(userId, marketOwner));
            if (uuidFromDB.isPresent()) {
                //uuid가 존재하면
                uuid = uuidFromDB.get();
                sessionToUUIDListMap.put(sessionId, uuid);
                log.info("[View] uuid 존재 : {}", uuid);
            } else {
                //uuid가 존재하지 않으면 새로운 채팅방 생성
                String newUUID = UUID.randomUUID().toString();
                talkDaoImpl.insert(newUUID, userId, marketOwner);
                sessionToUUIDListMap.put(sessionId, newUUID);
                log.info("[View] 새로운 uuid 생성 : {}", newUUID);
            }
        } else {
            //uuid가 view가 아니라면 uuid의 유효성 검사
            Optional<List> uuidFromDB = Optional.ofNullable(talkDaoImpl.getIdByMarketOwner(marketOwner));
            if(uuidFromDB.get().contains(uuid)){
                sessionToUUIDListMap.put(sessionId, uuid);
                log.info("uuid 존재 : {}", uuid);
            }else{
                log.info("잘못된 uuid");
                return;
            }
        }

    }

    @EventListener(SessionDisconnectEvent.class)
    public void onDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        log.info("[disconnect] connections : {}", sessionId);
        //접속 끊기면 삭제
        sessionToUUIDListMap.remove(sessionId);
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

    /*==================================================================*/
    /*==========================  채팅방 API  ==============================*/
    /*==================================================================*/
    @GetMapping("/chat/getChatList")
    public List<Map<String, Object>> getChatList(@RequestParam String marketOwner) {
        //마켓오너가 가지고 있는 채팅방 리스트를 가져온다
        List<String> chatList = talkDaoImpl.getIdByMarketOwner(marketOwner);
        List<Map<String, Object>> chatListMap = new ArrayList<>();
        for (String uuid : chatList) {
            Map<String, Object> chatMap = new HashMap<>();
            chatMap.put("uuid", uuid);
            List<? extends Object> mongoMessageList = chatMongoDbDao.findById(uuid).get().getMessageList();
            chatMap.put("chatList", mongoMessageList.get(mongoMessageList.size() - 1));
            chatMap.put("count", talkDaoImpl.getMarketOwnerCountByUuid(uuid));
            chatListMap.add(chatMap);
        }
        return chatListMap;
    }
}
