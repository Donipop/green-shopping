package com.green.shopping.dao.impl;


import com.green.shopping.dao.SearchDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public  List<String> searchview(HashMap<String, Object> map) {
        List<String> a =  sqlSession.selectList("search.searchview", map);

        return a;

    }
}

