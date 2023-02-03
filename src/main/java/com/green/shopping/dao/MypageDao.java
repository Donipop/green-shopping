package com.green.shopping.dao;

import com.green.shopping.vo.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MypageDao {
    List<CouponVo> Mypagecoupon();
    List<Shopping_basketVo> user_shopping_basket(String user_id);
    void user_shopping_basket_delete(HashMap<String, Object> map);

    List<ReviewVo> myreview(String user_id);

    List<MyPurchaseInquiryVo> mypruchaseinquiry(String user_id);

    TestpostVo invoiceNumberGet(long invoicenumber);
    
    int check_duplicate_nick(String user_nick);

    void myinfoUpdate(HashMap<String, Object> myinfo2);
    int countBasket(String user_id);

    String getmainImageByproductId(int productid);

    String getproductNameByproductId(int productid);

    HashMap<String, Object> getProductImgByProductId(int productid);

    HashMap<String, Object> getFile(String file_name);

    void MyPurchaseConfirm(HashMap<String, Object> map);
}
