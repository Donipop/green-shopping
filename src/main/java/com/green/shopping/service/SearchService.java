package com.green.shopping.service;

import com.green.shopping.dao.SearchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    private SearchDao searchDao;
    public List<HashMap<String, Object>> categorySearch(HashMap<String, Object> map) {
        List<HashMap<String, Object>> categorySearch = searchDao.categorySearch(map);
        return categorySearch;
    }

    public List<HashMap<String, Object>> AllcategorySearch(String searchcont) {
        List<HashMap<String, Object>> AllcategorySearch = searchDao.AllcategorySearch(searchcont);
        return AllcategorySearch;
    }

    public HashMap<String, Object> getProductValue(HashMap<String, Object> map) {
        HashMap<String, Object> b = searchDao.getProductValue(map);
        return b;
    }

    public HashMap<String, Object> getProductReview(HashMap<String, Object> map) {
        HashMap<String, Object> c = searchDao.getProductReview(map);
        return c;
    }

    public HashMap<String, Object> getProductImgByProductId(String id) {
        return searchDao.getProductImgByProductId(id);
    }

    public HashMap<String, Object> getFile(String file_name) {
        return searchDao.getFile(file_name);
    }

    public String categorynum(String name) {
        String categorynum = searchDao.categorynum(name);
        return categorynum;
    }
}
