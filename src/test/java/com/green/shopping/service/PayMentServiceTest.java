package com.green.shopping.service;

import com.green.shopping.dao.impl.PayMentDaoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PayMentServiceTest {

    @Autowired
    PayMentDaoImpl payMentDaoImpl;

    @Test
    @Transactional
    void insertPurcharse(){

    }
}