package com.green.shopping.dao;


public interface PayMentDao {
    int insertPurchaseList(String userId, int totalPrice, int productId, int delivery);
    int insertPurchaseDetailList(int price, int productDetailId, int count, int sale, int purchaseListId);
}
