package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PaymentVo {
    private int delivery;
    private String marketName;
    private int productId;
    private List<PaymentListItemVo> listItem;

    public PaymentVo() {
    }
}
