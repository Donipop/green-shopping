package com.green.shopping.service;

import com.green.shopping.dao.impl.FileDaoImpl;
import com.green.shopping.dao.impl.SellerCenterDaoImpl;
import com.green.shopping.vo.ProductUpdateVo;
import com.green.shopping.vo.ProductVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SellerCenterServiceTest {

    @Autowired
    SellerCenterDaoImpl sellerCenterDaoImpl;
    @Autowired
    FileDaoImpl fileDaoImpl;
    Logger logger = Logger.getLogger(SellerCenterServiceTest.class.getName());

    @Test
    @Transactional
    void getOrderList() {
        List<Map<String, Object>> productNumAndTitleList = sellerCenterDaoImpl.getProductIdAndTitleListByMarketName("아이유당근마켓");
        List<Map<String, Object>> purchaseList = new ArrayList<>();
        List<Map<String, Object>> totalOrderList = new ArrayList<>();
        for (int i = 0; i < productNumAndTitleList.size(); i++) {
            purchaseList.addAll(sellerCenterDaoImpl.getPurchasedListByProductId(productNumAndTitleList.get(i).get("ID")));
            for (Map<String, Object> purchase : purchaseList) {
                purchase.put("product_Title", productNumAndTitleList.get(i).get("TITLE"));
                totalOrderList.add(purchase);
            }
        }
        System.out.println("totalOrderList : " + totalOrderList);
    }

    @Test
    @Transactional
    void getOrderConfirm() {
        List<HashMap<String, Object>> list = sellerCenterDaoImpl.getOrderConfirm("아이유당근마켓");
        for (HashMap<String, Object> a : list) {
            System.out.println(a);
        }
    }

    @Test
    @Transactional
    void getOrderConfirmModal() {
        List<HashMap<String, Object>> list = sellerCenterDaoImpl.getOrderConfirmModal(5);
        for (HashMap<String, Object> a : list) {
            System.out.println(a);
        }
    }

    @Test
    @Transactional
    void testGetProductInfo() {
        //given
        //productId를 주면
        int productId = 730;
        //when
        Map<String, Object> productVo = sellerCenterDaoImpl.getProduct(productId);
        System.out.println(productVo);
        List<HashMap<String, Object>> productVoList = sellerCenterDaoImpl.getProductDetailByProductId(productId);
        //img 가져오기 시작
        List<HashMap<String, Object>> imgList = sellerCenterDaoImpl.getProductImgByProductId(productId);
        List<HashMap<String, Object>> fileList = new ArrayList<>();
        for (HashMap<String, Object> imgMap : imgList) {
            HashMap<String, Object> fileInfo = fileDaoImpl.getFile(imgMap.get("FILE_NAME").toString());
            HashMap<String, Object> fileMap = new HashMap<>();
            fileMap.put("FILE_PATH", fileInfo.get("NAME").toString() + "." + fileInfo.get("FILE_TYPE").toString());
            fileMap.put("ISMAIN", imgMap.get("ISMAIN").toString());
            fileList.add(fileMap);
        }
        //img 가져오기 끝
        System.out.println("fileList : " + fileList);

        //detailImg 가져오기 시작
        List<String> detailImgList = new ArrayList<>();
        fileList.stream().forEach(item -> {
            if (item.get("ISMAIN").toString().equals("0")) {
                detailImgList.add(item.get("FILE_PATH").toString());
            }
        });
        //detailImg 가져오기 끝


        ProductUpdateVo productUpdateVo = ProductUpdateVo.builder()
                .category(productVo.get("category").toString())
                .cont(productVo.get("cont").toString())
                .event(productVo.get("event").toString())
                .id(Integer.parseInt(productVo.get("id").toString()))
                .marketName(productVo.get("marketName").toString())
                .title(productVo.get("title").toString())
                .product(productVoList)
                .userId(sellerCenterDaoImpl.getSellerIdByMarketName(productVo.get("marketName").toString()))
                .mainImg(fileList.stream().filter(item -> item.get("ISMAIN").equals("1")).findFirst().get().get("FILE_PATH").toString())
                .detailImg(detailImgList)
                .build();

        System.out.println("productUpdateVo : " + productUpdateVo);
        //then
    }
}