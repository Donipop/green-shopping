package com.green.shopping.service;

import com.google.gson.Gson;
import com.green.shopping.Doc.ChatDoc;
import com.green.shopping.dao.ChatMongoDbDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class MongoServiceTest {

    @Autowired
    ChatMongoDbDao chatMongoDbDao;

    @Test
    void insertTest(){
//        ChatDoc chatDoc = new ChatDoc();
//        HashMap<String,String> map = new HashMap<>();
//        Gson gson = new Gson();
//        List<String> list = new ArrayList<>();
//        map.put("message","안녕하세요");
//        map.put("sender","user");
//        map.put("time", "2021-07-01 12:00:00");
//
//        list.add(gson.toJson(map));
//        map.clear();
//        map.put("message","안녕하세요2");
//        map.put("sender","user2");
//        map.put("time", "2022-07-01 12:00:00");
//        list.add(gson.toJson(map));
//        System.out.println(list);
//        chatDoc.set_id("sorkaksemsdkdlel1");
//        chatDoc.setMessageList(list);
//
//        Optional<ChatDoc> result = chatMongoDbDao.findById("sorkaksemsdkdlel1");
//        if(result.isEmpty()){
//            System.out.println("없음");
//        }else{
//            System.out.println("있음");
//            System.out.println(result);
//        }
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        System.out.println(uuid.length());

    }



}