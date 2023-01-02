package com.green.shopping.controller;


import com.green.shopping.service.MypageService;
import com.green.shopping.vo.CouponVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Mypage")
=======
import com.green.shopping.vo.Shopping_basketVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

@RestController
@RequestMapping("/mypage")
>>>>>>> cf8a995c89b9f5b39850088190f0f1f9c7f23f6a
public class MypageController {

    @Autowired
    MypageService mypageService;

<<<<<<< HEAD
    @GetMapping("/coupon")
    public List<CouponVo> Mypagecoupon() {

        return mypageService.Mypagecoupon();

    }

=======

    @PostMapping("/user_shopping_basket")
    public List<Shopping_basketVo> user_shopping_basket(@RequestParam String user_id) {

        List<Shopping_basketVo> user_shopping_basket = mypageService.user_shopping_basket(user_id);


        return user_shopping_basket;
    }

    @PostMapping("/delete_shopping_basket")
    public void user_shopping_basket_delete(@RequestParam String user_id, @RequestParam List<Integer> shoppingBasket_deleteList) {

        System.out.println("dd");
        System.out.println(shoppingBasket_deleteList);
        System.out.println(user_id);

    }
>>>>>>> cf8a995c89b9f5b39850088190f0f1f9c7f23f6a
}
