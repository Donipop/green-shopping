package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SellerCenterCreateVo {
    public String market_Name;
    public String Category;
    public String Title;
    public String Content;
    public String Event;
    public List<ProductVo> Product;
    public String MainImg;
    public List<String> DetailImg;
}
