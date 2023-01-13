package com.green.shopping.config;

import com.google.gson.Gson;
import com.green.shopping.vo.ProductImgVo;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> sessionList = new ArrayList<>();
    private static List<HashMap<String,Object>> chatList = new ArrayList<>();
    private static HashMap<String, Object> chatMap = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Gson gson = new Gson();
        HashMap<String,Object> textMap = gson.fromJson(message.getPayload(),HashMap.class);
        System.out.println(textMap);
        //{marketOwner=null, message=이제 한걸음 일뿐우우웅ㄴ, uuid=1234, userId=admin}


        for(WebSocketSession se: sessionList) {
            se.sendMessage(new TextMessage(gson.toJson(textMap)));
        }
    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        sessionList.add(session);

        System.out.println(session + " 클라이언트 접속");
        session.sendMessage(new TextMessage("서버 접속 성공"));
    }

    /* Client가 접속 해제 시 호출되는 메서드드 */

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        System.out.println(session + " 클라이언트 접속 해제");
        sessionList.remove(session);
    }
}
