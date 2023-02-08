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
    public List<HashMap<String, Object>> categorySearch(HashMap<String, Object> map) {
        List<HashMap<String, Object>> categorySearch =  sqlSession.selectList("search.searchview", map);

        return categorySearch;

    }

    @Override
    public List<HashMap<String, Object>> AllcategorySearch(String searchcont) {
        List<HashMap<String, Object>> AllcategorySearch = sqlSession.selectList("search.AllcategorySearch", searchcont);
        return AllcategorySearch;
    }

    @Override
    public  HashMap<String, Object> getProductValue(HashMap<String, Object> map) {
        HashMap<String, Object> b = sqlSession.selectOne("search.getProductValue", map);
        return b;

    }

    @Override
    public  HashMap<String, Object> getProductReview(HashMap<String, Object> map) {
        HashMap<String, Object> c = sqlSession.selectOne("search.getProductReview", map);
        return c;
    }

    @Override
    public HashMap<String, Object> getProductImgByProductId(String id) {
        return sqlSession.selectOne("search.getProductImgByProductId", id);
    }

    @Override
    public HashMap<String, Object> getFile(String file_name) {
        return sqlSession.selectOne("search.getFile", file_name);
    }

    @Override
    public String categorynum(String name) {

        String categorynum = sqlSession.selectOne("search.categorynum", name);
        return categorynum;
    }



}

