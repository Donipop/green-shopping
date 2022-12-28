package com.green.shopping.controller;

import com.green.shopping.service.PayMentService;
import com.green.shopping.vo.PaymentListItemVo;
import com.green.shopping.vo.PaymentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PayMentController {

    @Autowired
    private final PayMentService payMentService;

    public PayMentController(PayMentService payMentService) {
        this.payMentService = payMentService;
    }

    @PostMapping("/purchase")
    public String purchase(@RequestBody PaymentVo paymentVo) {
        return payMentService.insertPurchase(paymentVo);
    }
}
