package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class PurchaselistVo {
    private int id;
    private String user_id;
    private int totalprice;
    private int productid;
    private int state;
    private int delivery;
    private Date time;
    private int postaddressid;
    private String marketName;
    private String mainimage;
    private String title;

    public PurchaselistVo() {
    }

    public PurchaselistVo(int id, String user_id, int totalprice, int productid, int state, int delivery, Date time, int postaddressid, String marketName, String mainimage,String title) {
        this.id = id;
        this.user_id = user_id;
        this.totalprice = totalprice;
        this.productid = productid;
        this.state = state;
        this.delivery = delivery;
        this.time = time;
        this.postaddressid = postaddressid;
        this.marketName = marketName;
        this.mainimage = mainimage;
        this.title = title;
    }
}
