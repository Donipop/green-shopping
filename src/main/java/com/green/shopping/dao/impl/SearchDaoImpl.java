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
    public List<HashMap<String, Object>> Categorysearch(HashMap<String, Object> map) {
        List<HashMap<String, Object>> Categorysearch =  sqlSession.selectList("search.searchview", map);

        return Categorysearch;

    }

    @Override
    public List<HashMap<String, Object>> Allcategorysearch(String searchcont) {
        List<HashMap<String, Object>> Allcategorysearch = sqlSession.selectList("search.Allcategorysearch", searchcont);
        return Allcategorysearch;
    }

    @Override
    public  HashMap<String, Object> searchview1(HashMap<String, Object> map) {
        HashMap<String, Object> b = sqlSession.selectOne("search.searchview1", map);
        return b;

    }

    @Override
    public  HashMap<String, Object> searchview2(HashMap<String, Object> map) {
        HashMap<String, Object> c = sqlSession.selectOne("search.searchview2", map);
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

