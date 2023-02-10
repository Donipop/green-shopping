package com.green.shopping.dao.impl;

import com.green.shopping.dao.IndexDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class IndexDaoImpl implements IndexDao {
    @Autowired
    SqlSession sqlSession;


    @Override
    public  List<HashMap<String, Object>> randomitemlist() {
        List<HashMap<String, Object>> selectList = sqlSession.selectList("Index.randomitemlist");

        return selectList;
    }

    @Override
    public void insertRandomItem() {
        sqlSession.insert("Index.insertRandomItem");
    }

    @Override
    public List<HashMap<String, Object>> recommenditemlist() {
        return sqlSession.selectList("Index.recommenditemlist");

    }

    @Override
    public HashMap<String, Object> starCount(HashMap<String, Object> map) {
        return sqlSession.selectOne("Index.starCount", map);
    }

    @Override
    public List<Map<String, Object>> categoryItemList(Map<String,String> category) {
        return sqlSession.selectList("Index.categoryItemList", category);
    }
}

