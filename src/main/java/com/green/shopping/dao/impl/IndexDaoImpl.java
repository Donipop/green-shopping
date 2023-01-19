package com.green.shopping.dao.impl;

import com.green.shopping.dao.IndexDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class IndexDaoImpl implements IndexDao {
    @Autowired
    SqlSession sqlSession;


    @Override
    public  List<HashMap<String, Object>> randomitemlist() {
        List<HashMap<String, Object>> a = sqlSession.selectList("Index.randomitemlist");

        return a;
    }

    @Override
    public void insertRandomItem() {
        sqlSession.insert("Index.insertRandomItem");
    }
}

