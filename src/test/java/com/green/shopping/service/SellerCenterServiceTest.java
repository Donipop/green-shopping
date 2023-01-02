package com.green.shopping.service;

import com.green.shopping.dao.impl.SellerCenterDaoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SellerCenterServiceTest {

    @Autowired
    SellerCenterDaoImpl sellerCenterDaoImpl;

    @Test
    @Transactional
    void getOrderList(){
        List<Map<String,Object>> productNumAndTitleList = sellerCenterDaoImpl.getProductIdAndTitleListByMarketName("아이유당근마켓");
        List<Map<String,Object>> purchaseList = new ArrayList<>();
        List<Map<String, Object>> totalOrderList = new ArrayList<>();
        for (int i=0; i<productNumAndTitleList.size(); i++) {
            purchaseList.addAll(sellerCenterDaoImpl.getPurchasedListByProductId(productNumAndTitleList.get(i).get("ID")));
            for (Map<String, Object> purchase : purchaseList) {
                purchase.put("product_Title", productNumAndTitleList.get(i).get("TITLE"));
                totalOrderList.add(purchase);
            }
        }
        System.out.println("totalOrderList : " + totalOrderList);
    }

    @Test
    @Transactional
    void getOrderConfirm() {
        List<Map<String,Object>> list = sellerCenterDaoImpl.getOrderConfirm("아이유당근마켓");
        for(Map<String,Object> a : list){
            System.out.println(a);
        }
    }
}