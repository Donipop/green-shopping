package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QnAVo {
    private int id;
    private int product_num;
    private String qnatype;
    private String cont;
    private String user_id;
    private String regdate;
    private String product_name;

    private int child_id;

    public QnAVo() {
    }

    public QnAVo(int id, int product_num, String qnatype, String cont, String user_id, String regdate, String product_name, int child_id) {
        this.id = id;
        this.product_num = product_num;
        this.qnatype = qnatype;
        this.cont = cont;
        this.user_id = user_id;
        this.regdate = regdate;
        this.product_name = product_name;
        this.child_id = child_id;
    }
}
