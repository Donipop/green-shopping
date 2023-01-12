package com.green.shopping.dao;

import com.green.shopping.Doc.ChatDoc;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ChatMongoDbDao extends MongoRepository<ChatDoc,String> {
    String findByName(String name);

}
