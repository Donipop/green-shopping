package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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

    public SignUp() {}

    public SignUp(String username, String name, String email, String password, String brith, String address, int sex, String nick, String tel) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.brith = brith;
        this.address = address;
        this.sex = sex;
        this.nick = nick;
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "SignUp{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", brith=" + brith +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", nick='" + nick + '\'' +
                ", nick='" + tel + '\'' +
                '}';
    }
}
