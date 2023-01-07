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
    public  List<String> searchview(HashMap<String, Object> map) {
        List<String> a = searchDao.searchview(map);
        return a;
    }
}
