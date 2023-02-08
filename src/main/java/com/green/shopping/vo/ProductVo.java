package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVo {
    public int id;
    public String product_name;
    public String product_price;
    public String product_discount;
    public String product_count;
    public String dateStart;
    public String dateEnd;
    public int deleteCheck;
}
