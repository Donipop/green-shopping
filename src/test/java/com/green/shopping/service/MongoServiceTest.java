package com.green.shopping.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.shopping.Doc.ChatDoc;
import com.green.shopping.dao.ChatMongoDbDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MongoServiceTest {

    @Autowired
    ChatMongoDbDao chatMongoDbDao;

    @Test
    void insertTest(){
//        System.out.println(chatMongoDbDao.findAll().toString());

//        ChatDoc chatDoc = new ChatDoc();
//        chatDoc.setName("test11111");
//        chatDoc.setMessage("test22222");
//
//        chatMongoDbDao.insert(chatDoc);
//        ChatDoc chatDoc = chatMongoDbDao.findByName("nameTest");
//        System.out.println("count : " + chatMongoDbDao.count().toString());
        System.out.println(chatMongoDbDao.findByName("nameTest"));
//        System.out.println(chatMongoDbDao.findAll());

//        chatMongoDbDao.findAll().map(ChatDoc::toString).subscribe(System.out::println);
//        System.out.println(chatMongoDbDao.findAll());
    }



}