package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUp {
    private String username;
    private String name;
    private String email;
    private String password;
    private String password2;
    private String address;
    private int year;
    private int month;
    private int day;
}
