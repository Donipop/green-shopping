package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Shopping_basketVo {
    public int productDetailId;
    public int count;
    public int price;
    public String name;
    public String ProductId;
    public String marketName;
    public boolean checked = true;

}
