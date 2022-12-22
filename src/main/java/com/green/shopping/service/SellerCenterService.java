package com.green.shopping.service;

import com.green.shopping.dao.SellerCenterDao;
import com.green.shopping.dao.impl.SellerCenterDaoImpl;
import com.green.shopping.vo.CategoryVo;
import com.green.shopping.vo.ProductVo;
import com.green.shopping.vo.SellerCenterCreateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerCenterService {
    @Autowired
    private final SellerCenterDaoImpl sellerCenterDaoImpl;

    @Autowired
    private final FileService fileService;

    public SellerCenterService(SellerCenterDaoImpl SellerCenterDaoImpl, FileService fileService) {
        this.sellerCenterDaoImpl = SellerCenterDaoImpl;
        this.fileService = fileService;
    }

    public List<CategoryVo> getCategoryList(String parent_num)
    {
        return sellerCenterDaoImpl.geCategoryList(parent_num);
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
        //Product_Tb 등록
        int productCreate_and_Num = sellerCenterDaoImpl.createProduct(
                sellerCenterCreateVo.getMarket_Name(),
                sellerCenterCreateVo.getCategory(),
                sellerCenterCreateVo.getTitle(),
                sellerCenterCreateVo.getContent(),
                sellerCenterCreateVo.getEvent()
        );
        //ProductDetail_Tb 등록
        for (int i = 0; i < sellerCenterCreateVo.getProduct().size(); i++) {
            ProductVo productVo = sellerCenterCreateVo.getProduct().get(i);
            sellerCenterDaoImpl.createProductDetail(productCreate_and_Num, productVo.getName(), productVo.getPrice(), productVo.getDiscount(), productVo.getCount(), productVo.getDatestart(), productVo.getDateend());
        }
        //File_Tb에 파일 업로드
        //메인 이미지가 있을 경우에만 업로드 진행
        if (sellerCenterCreateVo.getMainImg() != null && !sellerCenterCreateVo.getMainImg().equals("")) {
            //썸네일 이미지 먼저 업로드
            String mainImgUpload = fileService.fileUpload(sellerCenterCreateVo.getMainImg(),sellerCenterCreateVo.getUserId());
            //File_Tb에 업로드한 파일의 Id받아서 Product_Img_Tb에 저장
            if (mainImgUpload != "error") {
                sellerCenterDaoImpl.createProductImg(mainImgUpload, productCreate_and_Num, "1");
            }
            //상세 이미지 업로드
            for (int i=0; i < sellerCenterCreateVo.getDetailImg().size(); i++){
                if(sellerCenterCreateVo.getDetailImg().get(i).length() > 0){
                    String detailImgUpload = fileService.fileUpload(sellerCenterCreateVo.getDetailImg().get(i), sellerCenterCreateVo.getUserId());
                    if(detailImgUpload != "error"){
                        sellerCenterDaoImpl.createProductImg(detailImgUpload, productCreate_and_Num, "0");
                    }
                }

            }
        }
        

        return "success";
    }
}
