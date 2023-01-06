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

    List<PurchaselistVo> mypruchaseinquiry(String user_id);

    TestpostVo invoiceNumberGet(long invoicenumber);

}
