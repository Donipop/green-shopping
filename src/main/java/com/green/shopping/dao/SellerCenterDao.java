package com.green.shopping.dao;

import com.green.shopping.vo.CategoryVo;
import com.green.shopping.vo.PurchaseDetailVo;
import com.green.shopping.vo.purchaseconfirmVo;

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
    List<purchaseconfirmVo> getPurchaseConfirm(HashMap<String, String> map);
    List<PurchaseDetailVo> getPurchasedDetailInfo(HashMap<String, Object> map);
    List<HashMap<String, Object>> getOrderConfirm(String marketName);
    List<HashMap<String, Object>> getOrderConfirmModal(int purchaseId);
    List<HashMap<String,Object>> getProductTbByMarketName(String marketName);
    HashMap<String,Object> getCategoryRoot(int num);
    List<HashMap<String,Object>> getProductDetailByProductId(int productId);
    List<HashMap<String,Object>> getProductImgByProductId(int productId);
    void updateProductTb(HashMap<String,Object> updateProductTbMap);
    void updateProductDetailTb(HashMap<String,Object> updateProductDetailTbMap);
    void updateProductImgTb(HashMap<String,Object> updateProductImgTbMap);
}
