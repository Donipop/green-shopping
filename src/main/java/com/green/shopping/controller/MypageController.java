package com.green.shopping.controller;


import com.green.shopping.service.MypageService;
import com.green.shopping.vo.Shopping_basketVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

@RestController
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    MypageService mypageService;


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
}
