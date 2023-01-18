package com.green.shopping.dao;

import java.util.Map;

public interface TalkDao {
    public void insert(String _id, String userId, String marketOwner);
    public Map select(String _id);
    public Map getIdByUserId(String userId);
    public Map getIdByMarketOwner(String marketOwner);
    public String getIdByUserIdAndMarketOwner(String userId,String marketOwner);
}
