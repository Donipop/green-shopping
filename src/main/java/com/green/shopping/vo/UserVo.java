package com.green.shopping.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo {
    private String user_id;
    private String user_password;
    private String user_email;
    private String user_name;
    private String user_brith;
    private String user_address;
    private int user_grade;
    private int user_money;
    private int user_sex;
    private String user_nick;
    private String user_signdate;
    private int user_role;
    private int user_state;

    public UserVo() {
    }

    public UserVo(String user_id, String user_password, String user_email, String user_name, String user_brith, String user_address, int user_grade, int user_money, int user_sex, String user_nick, String user_signdate, int user_role, int user_state) {
        this.user_id = user_id;
        this.user_password = user_password;
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_brith = user_brith;
        this.user_address = user_address;
        this.user_grade = user_grade;
        this.user_money = user_money;
        this.user_sex = user_sex;
        this.user_nick = user_nick;
        this.user_signdate = user_signdate;
        this.user_role = user_role;
        this.user_state = user_state;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "user_id='" + user_id + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_brith='" + user_brith + '\'' +
                ", user_address='" + user_address + '\'' +
                ", user_grade=" + user_grade +
                ", user_money=" + user_money +
                ", user_sex=" + user_sex +
                ", user_nick='" + user_nick + '\'' +
                ", user_signdate='" + user_signdate + '\'' +
                ", user_role=" + user_role +
                ", user_state=" + user_state +
                '}';
    }
}
