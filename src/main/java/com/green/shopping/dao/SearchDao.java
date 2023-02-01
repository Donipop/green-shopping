package com.green.shopping.dao;

import java.util.HashMap;
import java.util.List;

public interface SearchDao {
    List<HashMap<String, Object>> Categorysearch(HashMap<String, Object> map);

    List<HashMap<String, Object>> Allcategorysearch(String searchcont);

    HashMap<String, Object> searchview1(HashMap<String, Object> map);

    HashMap<String, Object> searchview2(HashMap<String, Object> map);

    HashMap<String, Object> getProductImgByProductId(String id);

    HashMap<String, Object> getFile(String file_name);

    String categorynum(String name);

}
