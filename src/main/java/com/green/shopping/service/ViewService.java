package com.green.shopping.service;

import com.green.shopping.dao.impl.ViewDaoImpl;
import com.green.shopping.vo.ProductImgVo;
import com.green.shopping.vo.ProductVo;
import com.green.shopping.vo.SellerCenterCreateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ViewService {

    @Autowired
    ViewDaoImpl viewDaoImpl;

    public SellerCenterCreateVo getProduct(int product_id) {
        SellerCenterCreateVo product = viewDaoImpl.getProduct(product_id);
        product.setProduct(getProductDetail(product_id));
        product.setProductId(product_id);
        return getProductImg(product,product_id);
    }

    private List<ProductVo> getProductDetail(int product_id) {
        return viewDaoImpl.getProductDetail(product_id);
    }

    private SellerCenterCreateVo getProductImg(SellerCenterCreateVo sellerCenterCreateVo, int porouct_id) {
        List<ProductImgVo> productImgs = viewDaoImpl.getProductImg(porouct_id);

        // 메인이미지
        String MainImg = productImgs.stream().filter
                        (productImgVo -> productImgVo.getIsMain() == 1)
                .findFirst().get().getFile_Name();
        // 서브이미지
        List<String> DetailImg = new ArrayList<>();
        productImgs.stream().filter(productImgVo -> productImgVo.getIsMain() == 0)
                .forEach(productImgVo -> DetailImg.add(productImgVo.getFile_Name()));

        //이미지에 확장자 찾아서 확장자 붙여주는 작업
        sellerCenterCreateVo.setMainImg(MainImg + "." + viewDaoImpl.getProductImgExtension(MainImg));

        for (int i = 0; i < DetailImg.size(); i++) {
            DetailImg.set(i, DetailImg.get(i) + "." + viewDaoImpl.getProductImgExtension(DetailImg.get(i)));
        }
        sellerCenterCreateVo.setDetailImg(DetailImg);

        return sellerCenterCreateVo;
    }
}
