package com.green.shopping.controller;

import com.green.shopping.Doc.ChatDoc;
import com.green.shopping.dao.ChatMongoDbDao;
import com.green.shopping.dao.impl.SellerCenterDaoImpl;
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
    private final Map<String, List<String>> userListMap = new HashMap<>();
    private final Map<String, Map> sessionToUUIDListMap = new HashMap<>();
    private final Map<String, List<Map>> chatListMap = new HashMap<>();
    private final ChatMongoDbDao chatMongoDbDao;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final TalkDaoImpl talkDaoImpl;
    private final SellerCenterDaoImpl sellerCenterDaoImpl;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate, ChatMongoDbDao chatMongoDbDao, TalkDaoImpl talkDaoImpl, SellerCenterDaoImpl sellerCenterDaoImpl) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatMongoDbDao = chatMongoDbDao;
        this.talkDaoImpl = talkDaoImpl;
        this.sellerCenterDaoImpl = sellerCenterDaoImpl;
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
        HashMap<String, Object> sendMessageMap = new HashMap<>();
        if (uuid.equals("view")) {
            sendMessageMap.put("TYPE", "Refresh");
            sendMessageMap.put("MESSAGE", "새로고침.");
            sendMessageMap.put("UUID", sessionToUUIDListMap.get(sessionId).get("uuid"));
            sendMessageMap.put("MARKETOWNER", sessionToUUIDListMap.get(sessionId).get("marketOwner"));
        } else {
            sendMessageMap.put("TYPE", "Connect");
            sendMessageMap.put("MESSAGE", "접속되었습니다.");
            sendMessageMap.put("UUID", uuid);
            sendMessageMap.put("MARKETOWNER", userId);
            sendMessageMap.put("CHATLIST", chatListMap.get(uuid));
        }
        //Map에 List가 없으면 생성하고 세션추가 없으면 세션만 추가
        userListMap.computeIfAbsent(sendMessageMap.get("UUID").toString(), k -> new ArrayList<>()).add(sessionId);
        simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/message", sendMessageMap, createHeaders(messageHeaderAccessor.getSessionId()));
    }

    @MessageMapping("/queue")
    @SendTo("/queue/user")
    public void sendMessageToUser(Map message, SimpMessageHeaderAccessor messageHeaderAccessor) {
        String sessionId = messageHeaderAccessor.getSessionId();
        String msg = message.get("message").toString();
        String uuid = message.get("uuid").toString();
        String userId = message.get("userId").toString();
        String marketOwner = message.get("marketOwner").toString();
        Map<String, Object> messageMap = new HashMap<>();
        Map<String, String> mongoDbDataMap = new HashMap<>();
        log.info(message.toString());
        mongoDbDataMap.put("sender", userId);
        mongoDbDataMap.put("message", msg);
        mongoDbDataMap.put("time", new Date().toString());
        chatListMap.get(uuid).add(mongoDbDataMap);
        messageMap.put("TYPE", "Message");
        messageMap.put("MESSAGE", msg);
        messageMap.put("UUID", uuid);
        messageMap.put("MARKETOWNER", marketOwner);
        messageMap.put("USERID", userId);
        simpMessagingTemplate.convertAndSend("/queue/user/" + uuid, messageMap);
        chatDataSave(userId, marketOwner, uuid);
    }

    private void chatDataSave(String userId, String marketOwner, String uuid) {
        //mongoDB에 저장
        chatMongoDbDao.save(new ChatDoc(uuid, chatListMap.get(uuid)));
        //oracleDB에 카운트 저장
        if (userId.equals(marketOwner)) {
            if (userListMap.get(uuid).size() > 1) {
                //두명이상 접속중일때
                talkDaoImpl.updateBothCount(uuid);
                log.info("[업데이트] 마켓오너 메세지 두명이상 접속중일때");
            } else {
                talkDaoImpl.updateMarketOwnerCount(uuid);
                log.info("[업데이트] 마켓오너 메세지");
            }
        } else {
            if (userListMap.get(uuid).size() > 1) {
                //두명이상 접속중일때
                talkDaoImpl.updateBothCount(uuid);
                log.info("[업데이트] 구매자 메세지 두명이상 접속중일때");
            } else {
                talkDaoImpl.updateUserIdCount(uuid);
                log.info("[업데이트] 구매자 메세지");
            }
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

        //로그인 되어있는 상태
        if (loginCheck.equals("true")) {
            //채팅방이 있는지 확인하고 없다면 생성
            if(checkChatRoom(userId, uuid, productId, marketOwner, sessionId).equals("success")){
                //이전 채팅내역 불러오기
                if(getBeforeChatList(sessionId).equals("success")){
                    log.info("[connect] 성공");
                }
            }

        }

    }

    private String getBeforeChatList(String sessionId) {
        //이전 채팅내역 불러오기
        String uuid = sessionToUUIDListMap.get(sessionId).get("uuid").toString();
        if (uuid == null) {
            return "fail";
        }
        Optional<Object> chatObject = Optional.ofNullable(chatMongoDbDao.findById(uuid));
        log.info("chatObject : {}", chatObject);
        if (!chatObject.get().equals(Optional.empty())) {
            Optional<ChatDoc> chatDoc = (Optional<ChatDoc>) chatObject.get();
            chatListMap.put(uuid, chatDoc.get().getMessageList());
            log.info("{} 채팅내역 불러옴", uuid);
        } else {
            log.info("{} 채팅내역 없음", uuid);
        }
        return "success";
    }

    private String checkChatRoom(String userId, String uuid, String productId, String marketOwner, String sessionId) {
        Map<String, String> itemMap = new HashMap<>();

        log.info("userID {} marketOwner {} uuid {} sessionId {} productId {}", userId, marketOwner, uuid, sessionId, productId);
        if (userId == null) {
            log.info("[잘못된 접근] userId null");
            return "fail";
        }
        if (uuid.equals("view")) {
            if (productId == null) {
                log.info("[잘못된 접근] productId null");
                return "fail";
            }
            //uuid => view 라면 view 페이지에서 접속한 것이므로
            //productId로 marketOwner를 구해옴
            marketOwner = talkDaoImpl.getMarketOwnerIdByProductId(Integer.parseInt(productId));
            //userId와 marketOwner를 비교
            if (userId.equals(marketOwner)) {
                //같으면 접속 불가
                log.info("[잘못된 접근] userId와 marketOwner가 같음");
                return "fail";
            }
            //userId와 marketOwner로 uuid를 구해옴
            Optional<String> uuidFromDB = Optional.ofNullable(talkDaoImpl.getIdByUserIdAndMarketOwner(userId, marketOwner));

            itemMap.put("marketOwner", marketOwner);
            if (uuidFromDB.isPresent()) {
                //uuid가 존재하면
                uuid = uuidFromDB.get();
                itemMap.put("uuid", uuid);
                sessionToUUIDListMap.put(sessionId, itemMap);
                log.info("[View] uuid 존재 : {}", uuid);
            } else {
                //uuid가 존재하지 않으면 새로운 채팅방 생성
                String newUUID = UUID.randomUUID().toString();
                talkDaoImpl.insert(newUUID, userId, marketOwner);
                chatMongoDbDao.save(new ChatDoc(newUUID, new ArrayList<>()));
                itemMap.put("uuid", newUUID);
                sessionToUUIDListMap.put(sessionId, itemMap);
                log.info("[View] 새로운 uuid 생성 : {}", newUUID);
            }
        } else {
            //uuid가 view가 아니라면 uuid의 유효성 검사
            Optional<List> uuidFromDB = Optional.ofNullable(talkDaoImpl.getIdByMarketOwner(marketOwner));
            if (uuidFromDB.get().contains(uuid)) {
                itemMap.put("uuid", uuid);
                itemMap.put("marketOwner", marketOwner);
                sessionToUUIDListMap.put(sessionId, itemMap);
                log.info("uuid 존재 : {}", uuid);
            } else {
                log.info("잘못된 uuid");
                return "fail";
            }
        }
        return "success";
    }

    @EventListener(SessionDisconnectEvent.class)
    public void onDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        log.info("[disconnect] connections : {}", sessionId);
        //접속 끊기면 삭제
        userListMap.get(sessionToUUIDListMap.get(sessionId).get("uuid")).remove(sessionId);
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
        log.info("marketOwner : {}", marketOwner);
        //마켓오너가 가지고 있는 채팅방 리스트를 가져온다
        List<String> chatList = talkDaoImpl.getIdByMarketOwner(marketOwner);
        List<Map<String, Object>> chatListMap = new ArrayList<>();
        for (String uuid : chatList) {
            Map<String, Object> chatMap = new HashMap<>();
            Optional<ChatDoc> chatDoc = chatMongoDbDao.findById(uuid);
            if(!chatDoc.isEmpty()){
                Map lastMessage = chatDoc.get().getMessageList().get(chatDoc.get().getMessageList().size() - 1);
                chatMap.put("uuid", uuid);
                chatMap.put("chatList", lastMessage);
                chatMap.put("count", talkDaoImpl.getMarketOwnerCountByUuid(uuid));
                chatListMap.add(chatMap);
                log.info("chatMap : {}", chatMap);
            }
        }
        log.info("chatListMap : {}", chatListMap);
        return chatListMap;
    }
    @GetMapping("/chat/ChatListByUserId")
    public List<Map<String,Object>> chatListByUserId(@RequestParam String userId){
        log.info("userId : {}", userId);
        //유저가 가지고 있는 채팅방 리스트를 가져온다
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<Map> chatList = talkDaoImpl.getIdByUserId(userId);
        for(Map forMap : chatList){
            Map<String, Object> map = new HashMap<>();
            Optional<ChatDoc> chatDoc = chatMongoDbDao.findById(forMap.get("ID").toString());
            if(chatDoc.isEmpty()){continue;}
            Map lastMessage = chatDoc.get().getMessageList().get(chatDoc.get().getMessageList().size() - 1);
            map.put("uuid", forMap.get("ID"));
            map.put("marketName",sellerCenterDaoImpl.getMarketNamebySellerid(forMap.get("MARKETOWNER").toString()));
            map.put("marketOwner", forMap.get("MARKETOWNER"));
            map.put("lastMessage", lastMessage.get("message"));
            map.put("count", talkDaoImpl.getUserIdCountByUuid(forMap.get("ID").toString()));
            resultList.add(map);
        }
        log.info("resultList : {}", resultList);
        return resultList;
    }
}
