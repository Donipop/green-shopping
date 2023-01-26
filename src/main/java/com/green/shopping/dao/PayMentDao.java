package com.green.shopping.dao;


import java.util.HashMap;

public interface PayMentDao {
    int insertPurchaseList(String userId, int totalPrice, int productId, int delivery,int postAddress, String marketName);
    int insertPurchaseDetailList(int price, int productDetailId, int count, int sale, int purchaseListId);

    HashMap<String,Object> getAddress(String userId);
}
