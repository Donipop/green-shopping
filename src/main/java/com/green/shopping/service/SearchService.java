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
    public List<HashMap<String, Object>> Categorysearch(HashMap<String, Object> map) {
        List<HashMap<String, Object>> Categorysearch = searchDao.Categorysearch(map);
        return Categorysearch;
    }

    public List<HashMap<String, Object>> Allcategorysearch(HashMap<String, Object> map) {
        List<HashMap<String, Object>> Allcategorysearch = searchDao.Allcategorysearch(map);
        return Allcategorysearch;
    }

    public HashMap<String, Object> searchview1(HashMap<String, Object> map) {
        HashMap<String, Object> b = searchDao.searchview1(map);
        return b;
    }

    public HashMap<String, Object> searchview2(HashMap<String, Object> map) {
        HashMap<String, Object> c = searchDao.searchview2(map);
        return c;
    }

    public HashMap<String, Object> getProductImgByProductId(String id) {
        return searchDao.getProductImgByProductId(id);
    }

    public HashMap<String, Object> getFile(String file_name) {
        return searchDao.getFile(file_name);
    }
}
