package com.green.shopping.dao;

import com.green.shopping.vo.CouponVo;
import com.green.shopping.vo.Shopping_basketVo;
import java.util.HashMap;
import java.util.List;

public interface MypageDao {
    List<CouponVo> Mypagecoupon();
    List<Shopping_basketVo> user_shopping_basket(String user_id);
    void user_shopping_basket_delete(HashMap<String, String> map);
}