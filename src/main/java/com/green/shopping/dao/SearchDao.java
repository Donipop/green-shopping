package com.green.shopping.dao;

import java.util.HashMap;
import java.util.List;

public interface SearchDao {
    List<HashMap<String, Object>> categorySearch (HashMap<String, Object> map);

    List<HashMap<String, Object>> AllcategorySearch(String searchcont);

    HashMap<String, Object> getProductValue(HashMap<String, Object> map);

    HashMap<String, Object> getProductReview(HashMap<String, Object> map);

    HashMap<String, Object> getProductImgByProductId(String id);

    HashMap<String, Object> getFile(String file_name);

    String categorynum(String name);

}
