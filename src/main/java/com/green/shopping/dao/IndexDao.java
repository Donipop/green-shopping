package com.green.shopping.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IndexDao {

    List<HashMap<String, Object>> randomitemlist();

    void insertRandomItem();

    List<HashMap<String, Object>> recommenditemlist();

    HashMap<String, Object> starCount(HashMap<String, Object> map);

    List<Map<String, Object>> categoryItemList(Map<String,String> category);
}
