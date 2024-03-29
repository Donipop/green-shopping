package com.green.shopping.service;

import com.green.shopping.dao.impl.PayMentDaoImpl;
import com.green.shopping.vo.PaymentListItemVo;
import com.green.shopping.vo.PaymentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;
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
    public String insertPurchase(PaymentVo paymentVo, Optional<String> userId, int postAddress) {
        try{
            //총결제금액 구하기
            int totalPrice = 0;
            for (PaymentListItemVo item : paymentVo.getListItem()) {
                totalPrice += item.getTotalPrice();
            }

            //purcharseList_Tb insert
            int purchaseListId = payMentDaoImpl.insertPurchaseList(userId.get(),totalPrice,paymentVo.getProductId(),paymentVo.getDelivery(),postAddress, paymentVo.getMarketName());

            //purcharseDetailList_Tb insert
            for (PaymentListItemVo item : paymentVo.getListItem()) {
                //(int price, int productDetailId, int count, int sale, int purchaseListId)
                payMentDaoImpl.insertPurchaseDetailList((item.getPrice()/item.getCount()),item.getProductDetailId(),item.getCount(),item.getDiscount(),purchaseListId);
            }
            // shopping_basket_Tb delete
            payMentDaoImpl.deleteShoppingBasket(userId.get(),paymentVo.getListItem().get(0).getProductDetailId());

            logger.info("insertPurchase : " + paymentVo.toString());
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    public HashMap<String,Object> getAddress(String userId) {
        return payMentDaoImpl.getAddress(userId);
    }
}
