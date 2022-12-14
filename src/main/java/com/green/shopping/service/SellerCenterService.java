package com.green.shopping.service;

import com.green.shopping.dao.SellerCenterDao;
import com.green.shopping.vo.CategoryVo;
import com.green.shopping.vo.SellerCenterCreateVo;
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

    public String create(SellerCenterCreateVo sellerCenterCreateVo) {
        if (sellerCenterCreateVo.getCategory() == null || sellerCenterCreateVo.getCategory().equals("")) {
            return "카테고리를 선택해주세요.";
        }
        if (sellerCenterCreateVo.getTitle() == null || sellerCenterCreateVo.getTitle().equals("")) {
            return "페이지 제목을 입력해주세요.";
        }
        if (sellerCenterCreateVo.getContent() == null || sellerCenterCreateVo.getContent().equals("")) {
            return "페이지 내용을 입력해주세요.";
        }
        if (sellerCenterCreateVo.getProduct() == null || sellerCenterCreateVo.getProduct().size() == 0) {
            return "상품을 추가해주세요.";
        }
        if (sellerCenterCreateVo.getMainImg() == null || sellerCenterCreateVo.getMainImg().equals("")) {
            return "메인 이미지를 추가해주세요.";
        }


        return "성공";
    }
}
