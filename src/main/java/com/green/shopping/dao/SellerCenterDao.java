package com.green.shopping.dao;

import com.green.shopping.vo.CategoryVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SellerCenterDao {
    List<CategoryVo> geCategoryList(String parent_num);
    int createProduct(String market_Name, String category, String title, String cont, String event);
    int createProductDetail(int Product_Id, String Product_Name, String Product_Price, String Product_Discount, String Product_Count, String DateStart, String DateEnd);
    String createProductImg(String fileId, int productNum, String isMainImg);
    List<Object> getOrderList(String marketName);
    List<Map<String,Object>> getProductIdAndTitleListByMarketName(String marketName);
    List<Map<String,Object>> getPurchasedListByProductId(Object productId);
    Map<String,Object> getPostAddressById(int postNum);
    List<Map<String,Object>> getOrderDetail(int orderNum);
    void updateOrderStatus(int orderNum, int status);
    void insertPostInfo(String invoiceNum, String companyName, int purchaseNum);
    List<HashMap<String, Object>> getOrderConfirm(String marketName);
}
