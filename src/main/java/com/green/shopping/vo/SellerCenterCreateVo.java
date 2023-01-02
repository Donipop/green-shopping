package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SellerCenterCreateVo {
    public String market_name;
    public String category;
    public String title;
    public String cont;
    public String event;
    public List<ProductVo> product;
    public String mainImg;
    public List<String> detailImg;
    public String userId;
    public int productId;
}
