package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeVo {
    private int id;
    private String user_id;
    private String title;
    private String cont;
    private int read_count;
    private String user_nick;

    private String indate;

    public NoticeVo() {
    }

    public NoticeVo(int id, String user_id, String title, String cont, int read_count, String user_nick, String indate) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.cont = cont;
        this.read_count = read_count;
        this.user_nick = user_nick;
        this.indate = indate;
    }
}
