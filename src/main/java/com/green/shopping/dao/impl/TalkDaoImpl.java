package com.green.shopping.dao.impl;

import com.green.shopping.dao.TalkDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class TalkDaoImpl implements TalkDao {

    @Autowired
    private final SqlSession sqlSession;

    public TalkDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void insert(String _id, String userId, String marketOwner) {
        Map<String, String> insertMap = new HashMap<>();
        insertMap.put("_id", _id);
        insertMap.put("userId", userId);
        insertMap.put("marketOwner", marketOwner);
        sqlSession.insert("Talk.insert", insertMap);
    }

    @Override
    public Map select(String _id) {
        return sqlSession.selectOne("Talk.select", _id);
    }

    @Override
    public List<Map> getIdByUserId(String userId) {
        return sqlSession.selectList("Talk.getIdByUserId", userId);
    }

    @Override
    public List<String> getIdByMarketOwner(String marketOwner) {
        return sqlSession.selectList("Talk.getIdByMarketOwner", marketOwner);
    }

    @Override
    public String getIdByUserIdAndMarketOwner(String userId, String marketOwner) {
        Map getIdByUserIdAndMarketOwnerMap = new HashMap();
        getIdByUserIdAndMarketOwnerMap.put("userId", userId);
        getIdByUserIdAndMarketOwnerMap.put("marketOwner", marketOwner);
        return sqlSession.selectOne("Talk.getIdByUserIdAndMarketOwner", getIdByUserIdAndMarketOwnerMap);
    }
}
