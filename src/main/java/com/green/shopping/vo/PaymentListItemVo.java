package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentListItemVo {
    private int count;
    private String name;
    private int price;
    private int productDetailId;

    public PaymentListItemVo() {
    }
}
