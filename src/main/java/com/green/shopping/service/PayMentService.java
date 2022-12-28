package com.green.shopping.service;

import com.green.shopping.dao.impl.PayMentDaoImpl;
import com.green.shopping.vo.PaymentListItemVo;
import com.green.shopping.vo.PaymentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class PayMentService {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private final PayMentDaoImpl payMentDaoImpl;

    public PayMentService(PayMentDaoImpl payMentDaoImpl) {
        this.payMentDaoImpl = payMentDaoImpl;
    }

    @Transactional
    public String insertPurchase(PaymentVo paymentVo){
        try{
            //총결제금액 구하기
            int totalPrice = 0;
            for (PaymentListItemVo item : paymentVo.getListItem()) {
                totalPrice += item.getPrice() * item.getCount();
            }
            totalPrice += paymentVo.getDelivery();

            //purcharseList_Tb insert
            int purchaseListId = payMentDaoImpl.insertPurchaseList("test",totalPrice,paymentVo.getProductId(),paymentVo.getDelivery());

            //purcharseDetailList_Tb insert
            for (PaymentListItemVo item : paymentVo.getListItem()) {
                int j = payMentDaoImpl.insertPurchaseDetailList(item.getPrice(),item.getProductDetailId(),item.getCount(),0,purchaseListId);
            }

            logger.info("insertPurchase : " + paymentVo.toString());
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

}
