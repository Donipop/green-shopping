package com.green.shopping.controller;


import com.green.shopping.service.FileService;
import com.green.shopping.service.MypageService;
import com.green.shopping.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.SimpleTimeZone;

@RestController
@RequestMapping("/mypage")
public class MypageController {
    @Autowired
    private final MypageService mypageService;
    @Autowired
    LoginController loginController;

    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }
    @GetMapping("/coupon")
    public List<CouponVo> Mypagecoupon() {
        return mypageService.Mypagecoupon();

    }

    @PostMapping("/user_shopping_basket")
    public List<Shopping_basketVo> user_shopping_basket(@RequestParam String user_id) {

        List<Shopping_basketVo> user_shopping_basket = mypageService.user_shopping_basket(user_id);


        return user_shopping_basket;
    }

    @PostMapping("/delete_shopping_basket")
    public void user_shopping_basket_delete(@RequestParam String user_id, @RequestParam String shoppingBasket_deleteList) {

        String[] shoppingBasket_deleteListArray = shoppingBasket_deleteList.split(",");
        List<String> shoppingBasket_deleteListArray2 = List.of(shoppingBasket_deleteListArray);

        HashMap<String, Object> map = new HashMap<>();

        for (int i = 0; i < shoppingBasket_deleteListArray2.size(); i++) {
            map.put("user_id", user_id);
            map.put("productDetailId", shoppingBasket_deleteListArray2.get(i));
            mypageService.user_shopping_basket_delete(map);
        }

    }

    @GetMapping("/myreview")
    public List<ReviewVo> myreview(@RequestParam String user_id) {
        List<ReviewVo> myReviewList = mypageService.myreview(user_id);
        for (int i = 0; i < myReviewList.size(); i++) {
            HashMap<String, Object> productImage = mypageService.getProductImgByProductId(myReviewList.get(i).getProduct_num());
            System.out.println(productImage);
            if (productImage != null) {
                HashMap<String, Object> fileMap = mypageService.getFile(productImage.get("FILE_NAME").toString());
                myReviewList.get(i).setProductimage(fileMap.get("NAME") + "." + fileMap.get("FILE_TYPE"));
            }
        }
        return myReviewList;
    }

    @GetMapping("/MyPurchaseInquiry")
    public List<MyPurchaseInquiryVo> mypurchaseinquiry(@RequestParam String user_id) {
        List<MyPurchaseInquiryVo> mypurchaseinquirylist = mypageService.mypruchaseinquiry(user_id);
        for(int i=0; i<mypurchaseinquirylist.size(); i++){
            HashMap<String,Object> productImage = mypageService.getProductImgByProductId(mypurchaseinquirylist.get(i).getProductid());
            if(productImage != null){
                HashMap<String,Object> fileMap = mypageService.getFile(productImage.get("FILE_NAME").toString());
                mypurchaseinquirylist.get(i).setProductimage(fileMap.get("NAME") + "." + fileMap.get("FILE_TYPE"));
            }
        }

        return mypurchaseinquirylist;
    }

    @GetMapping("/MyPurchaseInquiry/deliverytracking")
    public TestpostVo invoiceNumberGet(@RequestParam long invoicenumber) {

        return mypageService.invoiceNumberGet(invoicenumber);
    }

    @PostMapping("/checkDuplicateNick")
    public Boolean checkDuplicateNick(@RequestParam String user_nick) {
        int count = mypageService.check_duplicate_nick(user_nick);
        if (count == 0) {
            return true;
        } else {
            return false;
        }

    }
    @PostMapping("/myinfoUpdate")
    public int myinfoUpdate(@RequestBody HashMap<String, Object> myinfo) {

        HashMap<String, Object> myinfo2 = (HashMap<String, Object>) myinfo.get("myinfo");
        String refreshToken = (String) myinfo.get("refreshToken");
        System.out.println(myinfo2);
        System.out.println(refreshToken);

        try {
            mypageService.myinfoUpdate(myinfo2);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
    @GetMapping("countBasket")
    public int countBasket(@RequestParam String user_id){
        return mypageService.countBasket(user_id);
    }
}
