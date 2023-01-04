package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TestpostVo {

    private long invoicenumber;
    private String content;


    public TestpostVo() {
    }

    public TestpostVo(long invoicenumber, String content) {
        this.invoicenumber = invoicenumber;
        this.content = content;
    }
}
