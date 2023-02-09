package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyPurchaseInquiryVo {
    private int id;
    private int productid;
    private int state;
    private String time;
    private String mainimage;
    private String title;
    private long totalprice;
    private int count;
    private String productimage;
    private int price;
    private int purchaseid;

    private int sale;

    public MyPurchaseInquiryVo() {
    }

    public MyPurchaseInquiryVo(int id,int state, String time, String mainimage, String title, long totalprice, int count, int productid, String productimage,int price,int purchaseid,int sale) {
        this.id = id;
        this.state = state;
        this.time = time;
        this.mainimage = mainimage;
        this.title = title;
        this.totalprice = totalprice;
        this.count = count;
        this.productid = productid;
        this.productimage = productimage;
        this.price = price;
        this.purchaseid = purchaseid;
        this.sale = sale;
    }



}
