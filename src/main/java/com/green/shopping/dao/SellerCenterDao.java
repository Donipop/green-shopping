package com.green.shopping.dao;

import com.green.shopping.vo.CategoryVo;

import java.util.List;

public interface SellerCenterDao {
    List<CategoryVo> geCategoryList(String parent_num);
    int createProduct(String market_Name, String category, String title, String cont, String event);
    int createProductDetail(int Product_Id, String Product_Name, String Product_Price, String Product_Discount, String Product_Count, String DateStart, String DateEnd);
    String createProductImg(String fileId, int productNum, String isMainImg);
}
