package com.green.shopping.vo;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class ProductUpdateVo {
    private String category;
    private String cont;
    private List<String> detailImg;
    private String event;
    private int id;
    private String mainImg;
    private String marketName;
    private List<HashMap<String,Object>> product;
    private String title;
    private String userId;
    @Builder
    public ProductUpdateVo(String category, String cont, List<String> detailImg, String event, int id, String mainImg, String marketName, List<HashMap<String, Object>> product, String title, String userId) {
        this.category = category;
        this.cont = cont;
        this.detailImg = detailImg;
        this.event = event;
        this.id = id;
        this.mainImg = mainImg;
        this.marketName = marketName;
        this.product = product;
        this.title = title;
        this.userId = userId;
    }
}
