package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private String time;
    private int postaddressid;
    private String marketName;

    public PurchaselistVo() {
    }

    public PurchaselistVo(int id, String user_id, int totalprice, int productid, int state, int delivery, String time, int postaddressid, String marketName) {
        this.id = id;
        this.user_id = user_id;
        this.totalprice = totalprice;
        this.productid = productid;
        this.state = state;
        this.delivery = delivery;
        this.time = time;
        this.postaddressid = postaddressid;
        this.marketName = marketName;
    }
}
