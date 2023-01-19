package com.green.shopping.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public interface IndexDao {

     List<HashMap<String, Object>> randomitemlist();
}
