package com.green.shopping.service;

import com.green.shopping.dao.MypageDao;
import com.green.shopping.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MypageService {
    @Autowired
    private final MypageDao mypageDao;

    public MypageService(MypageDao mypageDao) {
        this.mypageDao = mypageDao;
    }

    public List<CouponVo> Mypagecoupon() {
        return mypageDao.Mypagecoupon();
    }

    public List<Shopping_basketVo> user_shopping_basket(String user_id) {

        List<Shopping_basketVo> user_shopping_basket = mypageDao.user_shopping_basket(user_id);

        return user_shopping_basket;
    }

    public void user_shopping_basket_delete(HashMap<String, Object> map) {
        mypageDao.user_shopping_basket_delete(map);
    }

    public List<ReviewVo> myreview(String user_id) {
        return mypageDao.myreview(user_id);
    }

    public List<MyPurchaseInquiryVo> mypruchaseinquiry(String user_id) {
        return mypageDao.mypruchaseinquiry(user_id);
    }

    public TestpostVo invoiceNumberGet(long invoicenumber) {
        return mypageDao.invoiceNumberGet(invoicenumber);
    }

    public int check_duplicate_nick(String user_nick) {
        return mypageDao.check_duplicate_nick(user_nick);
    }

    public void myinfoUpdate(HashMap<String, Object> myinfo2) {
        mypageDao.myinfoUpdate(myinfo2);
    }

    public int countBasket(String user_id) {
        return mypageDao.countBasket(user_id);
    }

    public String getmainImageByproductId(int productid) {
        return mypageDao.getmainImageByproductId(productid);
    }

    public String getproductNameByproductId(int productid) {
        return mypageDao.getproductNameByproductId(productid);
    }

    public HashMap<String, Object> getProductImgByProductId(int productid) {
        return mypageDao.getProductImgByProductId(productid);
    }

    public HashMap<String, Object> getFile(String file_name) {
        return mypageDao.getFile(file_name);
    }
}