package com.green.shopping.dao;

import com.green.shopping.vo.CategoryVo;

import java.util.List;

public interface SellerCenterDao {
    List<CategoryVo> geCategoryList(String parent_num);
}
