package com.green.shopping.dao.impl;

import com.green.shopping.dao.PayMentDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class PayMentDaoImpl implements PayMentDao {
    @Autowired
    private final SqlSession sqlSession;

    public PayMentDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public int insertPurchaseList(String userId, int totalPrice, int productId, int delivery, int postAddress) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("totalPrice", totalPrice);
        map.put("productId", productId);
        map.put("delivery", delivery);
        map.put("postAddress",postAddress);

        sqlSession.insert("Payment.insertPurchaseList", map);
        return (int) map.get("id");
    }

    @Override
    public int insertPurchaseDetailList(int price, int productDetailId, int count, int sale, int purchaseListId) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("price", price);
        map.put("productDetailId", productDetailId);
        map.put("count", count);
        map.put("sale", sale);
        map.put("purchaseListId", purchaseListId);

        sqlSession.insert("Payment.insertPurchaseDetailList", map);
        return (int) map.get("id");
    }

    @Override
    public HashMap<String,Object> getAddress(String userId) {
        return sqlSession.selectOne("Payment.getAddress", userId);
    }
}
