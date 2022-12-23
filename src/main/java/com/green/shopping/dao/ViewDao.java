package com.green.shopping.dao;

import com.green.shopping.vo.ProductImgVo;
import com.green.shopping.vo.ProductVo;
import com.green.shopping.vo.SellerCenterCreateVo;

import java.util.HashMap;
import java.util.List;

public interface ViewDao {
    SellerCenterCreateVo getProduct(int product_id);
    List<ProductVo> getProductDetail(int product_id);
    List<ProductImgVo> getProductImg(int product_id);
    String getProductImgExtension(String img_id);
}
