package com.green.shopping.service;

import com.green.shopping.dao.IndexDao;
import com.green.shopping.dao.impl.IndexDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class IndexService {
    @Autowired
    IndexDao indexDao;
    public  List<HashMap<String, Object>> randomitemlist() {
        return indexDao.randomitemlist();
    }

    public void insertRandomItem() {
        indexDao.insertRandomItem();
    }
}
