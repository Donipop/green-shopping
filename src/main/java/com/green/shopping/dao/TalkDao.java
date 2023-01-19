package com.green.shopping.dao;

import java.util.List;
import java.util.Map;

public interface TalkDao {
    public void insert(String _id, String userId, String marketOwner);
    public Map select(String _id);
    public List<Map> getIdByUserId(String userId);
    public List<String> getIdByMarketOwner(String marketOwner);
    public String getIdByUserIdAndMarketOwner(String userId,String marketOwner);
}
