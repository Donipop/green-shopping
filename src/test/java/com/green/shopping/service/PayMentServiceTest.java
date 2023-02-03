package com.green.shopping.service;

import com.green.shopping.dao.impl.PayMentDaoImpl;
import com.green.shopping.vo.PaymentListItemVo;
import com.green.shopping.vo.PaymentVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PayMentServiceTest {

    @Autowired
    PayMentDaoImpl payMentDaoImpl;

    @Test
    @Transactional
    void insertPurchase(){


        PaymentVo paymentVo = new PaymentVo();
        PaymentListItemVo paymentListItemVo = new PaymentListItemVo();
        List<PaymentListItemVo> paymentListItemVoList = new ArrayList<>();

        paymentListItemVo.setCount(1);
        paymentListItemVo.setName("페이먼트 테스트제품이름");
        paymentListItemVo.setPrice(10000);
        paymentListItemVo.setProductDetailId(202);
        paymentListItemVoList.add(paymentListItemVo);

        paymentVo.setDelivery(2500);
        paymentVo.setMarketName("테스트마켓이름");
        paymentVo.setListItem(paymentListItemVoList);
        paymentVo.setProductId(79);

        //purcharseList_Tb insert
//        int purchaseListId = payMentDaoImpl.insertPurchaseList("test",150000,paymentVo.getProductId(),paymentVo.getDelivery());
        System.out.println(paymentVo.toString());
//        System.out.println("insertPurchaseList : " + purchaseListId);

        //purcharseDetailList_Tb insert
        for (PaymentListItemVo item : paymentVo.getListItem()) {
//            int j = payMentDaoImpl.insertPurchaseDetailList(item.getPrice(),item.getProductDetailId(),item.getCount(),0,purchaseListId);
//            System.out.println("insertPurchaseDetailList : " + j);
        }


    }
}