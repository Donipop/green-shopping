package com.green.shopping.service;

<<<<<<< HEAD

import com.green.shopping.dao.MypageDao;
import com.green.shopping.vo.CouponVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

=======
import com.green.shopping.dao.MypageDao;
import com.green.shopping.vo.Shopping_basketVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
>>>>>>> cf8a995c89b9f5b39850088190f0f1f9c7f23f6a
import java.util.List;

@Service
public class MypageService {

    @Autowired
<<<<<<< HEAD
    MypageDao mypagedao;

    public List<CouponVo> Mypagecoupon(){
        return mypagedao.Mypagecoupon();
=======
    MypageDao mypageDao;

    public List<Shopping_basketVo> user_shopping_basket(String user_id) {

        List<Shopping_basketVo> user_shopping_basket = mypageDao.user_shopping_basket(user_id);

        return user_shopping_basket;
    }

    public void user_shopping_basket_delete(HashMap<String, String> map) {
        mypageDao.user_shopping_basket_delete(map);
>>>>>>> cf8a995c89b9f5b39850088190f0f1f9c7f23f6a
    }
}
