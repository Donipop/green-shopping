package com.green.shopping.dao;

import java.util.HashMap;
import java.util.List;

public interface SearchDao {
    List<String> searchview(HashMap<String, Object> map);
}
