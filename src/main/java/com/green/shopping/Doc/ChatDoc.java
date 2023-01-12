package com.green.shopping.Doc;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat")
@Getter
@Setter
@ToString
public class ChatDoc {
    @Id
    private String _id;
    private String name;
    private String message;
}
