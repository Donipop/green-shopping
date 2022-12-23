package com.green.shopping.service;

import com.green.shopping.dao.impl.SellerCenterDaoImpl;
import com.green.shopping.dao.impl.ViewDaoImpl;
import com.green.shopping.vo.ProductImgVo;
import com.green.shopping.vo.ProductVo;
import com.green.shopping.vo.SellerCenterCreateVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class ViewServiceTest {

    @Autowired
    ViewDaoImpl viewDaoImpl;

    @Test
    @Transactional
    void getProduct() {
        SellerCenterCreateVo product = viewDaoImpl.getProduct(70);
        System.out.println(product);
    }

    @Test
    @Transactional
    void getProductDetail() {
        List<ProductVo> productVos = viewDaoImpl.getProductDetail(70);
        System.out.println(productVos);
    }

    @Test
    @Transactional
    void getProductImg() {
        List<ProductImgVo> productImgs = viewDaoImpl.getProductImg(71);

        String MainImg = productImgs.stream().filter
                        (productImgVo -> productImgVo.getIsMain() == 1)
                .findFirst().get().getFile_Name();
        List<String> DetailImg = new ArrayList<>();
        productImgs.stream().filter(productImgVo -> productImgVo.getIsMain() == 0)
                .forEach(productImgVo -> DetailImg.add(productImgVo.getFile_Name()));

        System.out.println(MainImg);
        System.out.println("==================");
        for (String s : DetailImg) {
            System.out.println(s);
        }

    }

}