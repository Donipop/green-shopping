package com.green.shopping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.shopping.service.PayMentService;
import com.green.shopping.vo.PaymentListItemVo;
import com.green.shopping.vo.PaymentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PayMentController {

    @Autowired
    private final PayMentService payMentService;

    public PayMentController(PayMentService payMentService) {
        this.payMentService = payMentService;
    }

    @PostMapping("/purchase")
    public String purchase(@RequestBody HashMap<String,Object> paymentVo) {
        ObjectMapper mapper = new ObjectMapper();
        PaymentVo mapToPaymentVo = mapper.convertValue(paymentVo.get("paymentVo"), PaymentVo.class);
        return payMentService.insertPurchase(mapToPaymentVo, Optional.ofNullable((String) paymentVo.get("userId")), (int) paymentVo.get("postAddress"));
    }

    @GetMapping("/getAddress")
    public HashMap<String,Object> getAddress(@RequestParam String userId) {
        return payMentService.getAddress(userId);
    }
}
