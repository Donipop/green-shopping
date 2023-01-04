package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ReviewVo {
    private int id;
    private String title;
    private String cont;
    private String time;
    private int product_num;
    private String user_id;
    private int star;
    private String regdate;
    public ReviewVo() {
    }

    public ReviewVo(int id, String title, String cont, String time, int product_num, String user_id, int star, String regdate) {
        this.id = id;
        this.title = title;
        this.cont = cont;
        this.time = time;
        this.product_num = product_num;
        this.user_id = user_id;
        this.star = star;
        this.regdate = regdate;
    }
}
