package com.green.shopping.service;

import com.green.shopping.dao.impl.PayMentDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayMentService {

    @Autowired
    private final PayMentDaoImpl payMentDaoImpl;

    public PayMentService(PayMentDaoImpl payMentDaoImpl) {
        this.payMentDaoImpl = payMentDaoImpl;
    }
}
