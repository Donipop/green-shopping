package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PurchaseDetailVo {
    private int id;
    private int price;
    private int count;
    private int sale;
    private int productdetailid;
    private int purchaselistid;
    private String product_name;
}
