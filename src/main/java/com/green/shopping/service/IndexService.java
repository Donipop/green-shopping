package com.green.shopping.service;

import com.green.shopping.dao.IndexDao;
import com.green.shopping.dao.impl.IndexDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexService {

    @Autowired
    IndexDao indexDao;

    public List<HashMap<String, Object>> randomitemlist() {
        return indexDao.randomitemlist();
    }

    public void insertRandomItem() {
        indexDao.insertRandomItem();
    }

    public List<HashMap<String, Object>> recommenditemlist() {
        return indexDao.recommenditemlist();
    }

    public HashMap<String, Object> starCount(HashMap<String, Object> map) {
        return indexDao.starCount(map);
    }

    public List<Map<String, Object>> categoryItemList(String[] category) {
        Map<String, String> categoryMap = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (category.length > i) {
                categoryMap.put("category" + (i + 1), category[i]);
            } else {
                categoryMap.put("category" + (i + 1), "999999");
            }
        }

        for (Map<String, Object> map : indexDao.categoryItemList(categoryMap)) {
            HashMap<String, Object> starMap = new HashMap<>();
            HashMap<String, Object> starCount = starCount((HashMap<String, Object>) map);

            starMap.putAll(map);
            if (starCount.get("star") == null) {
                starMap.put("star", 0);
                starMap.put("starCount", 0);
            } else {
                starMap.put("star", starCount.get("star"));
                starMap.put("starCount", starCount.get("starCount"));
            }
            resultList.add(starMap);
        }
        return resultList;
    }
}
