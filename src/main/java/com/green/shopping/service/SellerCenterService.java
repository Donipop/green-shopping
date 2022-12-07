package com.green.shopping.service;

import com.green.shopping.dao.SellerCenterDao;
import com.green.shopping.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerCenterService {
    @Autowired
    private final SellerCenterDao sellerCenterDao;

    public SellerCenterService(SellerCenterDao sellerCenterDao) {
        this.sellerCenterDao = sellerCenterDao;
    }

    public List<CategoryVo> getCategoryList(String parent_num) {
        return sellerCenterDao.geCategoryList(parent_num);
    }
}
