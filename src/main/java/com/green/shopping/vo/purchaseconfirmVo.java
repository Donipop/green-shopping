package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class purchaseconfirmVo {
    public int id;
    public String marketname;
    public String sellerid;
    public String title;
    public String buyerid;
    public int totalprice;
    public int delivery;
    public String purchasetime;
    public String purchaseconfirmtime;
}
