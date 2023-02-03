package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SignUp {
    private String username;
    private String name;
    private String email;
    private String password;
    private String brith;
    private String address;
    private int sex;
    private String nick;
    private String tel;
}
