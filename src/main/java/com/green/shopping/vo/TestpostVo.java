package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TestpostVo {

    private int invoicenumber;
    private String content;


    public TestpostVo() {
    }

    public TestpostVo(int invoicenumber, String content) {
        this.invoicenumber = invoicenumber;
        this.content = content;
    }
}
