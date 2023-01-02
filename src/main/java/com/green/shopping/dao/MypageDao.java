package com.green.shopping.dao;

<<<<<<< HEAD
import com.green.shopping.vo.CouponVo;

import java.util.List;

public interface MypageDao {
    List<CouponVo> Mypagecoupon();
=======
import com.green.shopping.vo.Shopping_basketVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface MypageDao {
    List<Shopping_basketVo> user_shopping_basket(String user_id);

    void user_shopping_basket_delete(HashMap<String, String> map);
>>>>>>> cf8a995c89b9f5b39850088190f0f1f9c7f23f6a
}
