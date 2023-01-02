package com.green.shopping.service;


import com.green.shopping.dao.MypageDao;
import com.green.shopping.vo.CouponVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MypageService {

    @Autowired
    MypageDao mypagedao;

    public List<CouponVo> Mypagecoupon(){
        return mypagedao.Mypagecoupon();
    }
}
