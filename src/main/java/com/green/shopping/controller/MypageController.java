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
public class MypageController {

    @Autowired
    MypageService mypageService;

    @GetMapping("/coupon")
    public List<CouponVo> Mypagecoupon() {

        return mypageService.Mypagecoupon();

    }

}
