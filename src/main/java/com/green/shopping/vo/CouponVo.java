package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CouponVo {
    private int id;
    private String name;
    private int condition;
    private int discount;
    private int discountrate;

    public CouponVo() {
    }

    public CouponVo(int id, String name, int condition, int disconut, int disconutrate) {
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.discount = disconut;
        this.discountrate = disconutrate;
    }
}
