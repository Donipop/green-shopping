package com.green.shopping.service;

import com.green.shopping.dao.SellerCenterDao;
import com.green.shopping.dao.impl.SellerCenterDaoImpl;
import com.green.shopping.vo.CategoryVo;
import com.green.shopping.vo.ProductVo;
import com.green.shopping.vo.SellerCenterCreateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (sellerCenterCreateVo.getCont() == null || sellerCenterCreateVo.getCont().equals("")) {
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
                sellerCenterCreateVo.getMarket_name(),
                sellerCenterCreateVo.getCategory(),
                sellerCenterCreateVo.getTitle(),
                sellerCenterCreateVo.getCont(),
                sellerCenterCreateVo.getEvent()
        );
        //ProductDetail_Tb 등록
        for (int i = 0; i < sellerCenterCreateVo.getProduct().size(); i++) {
            ProductVo productVo = sellerCenterCreateVo.getProduct().get(i);
            sellerCenterDaoImpl.createProductDetail(productCreate_and_Num, productVo.getProduct_name(), productVo.getProduct_price(), productVo.getProduct_discount(), productVo.getProduct_count(), productVo.getDateStart(), productVo.getDateEnd());
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
                    //System.out.println("detailImgUpload : " + detailImgUpload);
                    if(detailImgUpload != "error"){
                        sellerCenterDaoImpl.createProductImg(detailImgUpload, productCreate_and_Num, "0");
                    }
                }

            }
        }
        return "success";
    }

    public List<Map<String,Object>> getOrderList(String marketName){
        List<Map<String,Object>> productNumAndTitleList = sellerCenterDaoImpl.getProductIdAndTitleListByMarketName(marketName);
        List<Map<String,Object>> purchaseList = new ArrayList<>();
        List<Map<String, Object>> totalOrderList = new ArrayList<>();
        for (int i=0; i<productNumAndTitleList.size(); i++) {
            purchaseList.addAll(sellerCenterDaoImpl.getPurchasedListByProductId(productNumAndTitleList.get(i).get("ID")));
            for (Map<String, Object> purchase : purchaseList) {
                purchase.put("product_Title", productNumAndTitleList.get(i).get("TITLE"));
                totalOrderList.add(purchase);
            }
        }
        return totalOrderList;
    }

    public Map<String, Object> getPostAddress(int postNum) {
        return sellerCenterDaoImpl.getPostAddressById(postNum);
    }

    public List<Map<String, Object>> getOrderDetail(int orderNum) {
        return sellerCenterDaoImpl.getOrderDetail(orderNum);
    }
    public void updateOrderStatus(int orderNum, int status) {
        sellerCenterDaoImpl.updateOrderStatus(orderNum, status);
    }
    public void insertPostInfo(List<Map<String,Object>> postInfoList) {
        for (int i=0; i<postInfoList.size(); i++) {
            //post_Tb에 추가
            sellerCenterDaoImpl.insertPostInfo(postInfoList.get(i).get("invoiceNum").toString(), postInfoList.get(i).get("companyName").toString(),Integer.parseInt(postInfoList.get(i).get("purchaseNum").toString()));
            //purchaselist_tb에 상태 3[배송중]으로 변경
            sellerCenterDaoImpl.updateOrderStatus(Integer.parseInt(postInfoList.get(i).get("purchaseNum").toString()), 3);
        }
    }

    public List<HashMap<String, Object>> getOrderConfirm(String marketName) {
        return sellerCenterDaoImpl.getOrderConfirm(marketName);
    }

    public List<HashMap<String,Object>> getOrderConfirmModal(int purchaseNum) {
        return sellerCenterDaoImpl.getOrderConfirmModal(purchaseNum);
    }

    public List<HashMap<String,Object>> getProductTbByMarketName(String marketName) {
        return sellerCenterDaoImpl.getProductTbByMarketName(marketName);
    }
    public HashMap<String,Object> getCategoryRoot(int num){
        return sellerCenterDaoImpl.getCategoryRoot(num);
    }
    public List<HashMap<String,Object>> getProductDetailByProductId(int productId){
        return sellerCenterDaoImpl.getProductDetailByProductId(productId);
    }
    public List<HashMap<String,Object>> getProductImgByProductId(int productId){
        return sellerCenterDaoImpl.getProductImgByProductId(productId);
    }
    public void updateProduct(SellerCenterCreateVo sellerCenterCreateVo){
        System.out.println(sellerCenterCreateVo);
        //product_tb 수정
        

    }
}
