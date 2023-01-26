package com.green.shopping.dao;

import java.util.List;
import java.util.Map;

public interface TalkDao {
    public void insert(String _id, String userId, String marketOwner);
    public Map select(String _id);
    public List<Map> getIdByUserId(String userId);
    public List<String> getIdByMarketOwner(String marketOwner);
    public String getIdByUserIdAndMarketOwner(String userId,String marketOwner);
    public void updateMarketOwnerCount(String uuid);
    public void updateUserIdCount(String uuid);
    public void updateCurrentCount(String uuid);
    public void updateBothCount(String uuid);
    public void updateMarketOwnerCountToCurrent(String uuid);
    public void updateUserCountToCurrent(String uuid);
    public int getMarketOwnerCountByUuid(String uuid);
    public int getUserIdCountByUuid(String uuid);
    public String getMarketOwnerByUuid(String uuid);
    public String getMarketOwnerIdByProductId(int productId);
}
