package com.green.shopping.Doc;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "chat")
@Getter
@Setter
@ToString
public class ChatDoc {
    @Id
    private String _id;
    private List<Map> messageList;
}
