package com.green.shopping.service;

import com.green.shopping.dao.impl.FileDaoImpl;
import com.green.shopping.dao.impl.SellerCenterDaoImpl;
import com.green.shopping.vo.CategoryVo;
import com.green.shopping.vo.ProductVo;
import com.green.shopping.vo.ReviewVo;
import com.green.shopping.vo.SellerCenterCreateVo;
import com.green.shopping.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private final FileDaoImpl fileDaoImpl;
    public SellerCenterService(SellerCenterDaoImpl SellerCenterDaoImpl, FileService fileService, FileDaoImpl fileDaoImpl) {
        this.sellerCenterDaoImpl = SellerCenterDaoImpl;
        this.fileService = fileService;
        this.fileDaoImpl = fileDaoImpl;
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
    @Transactional
    public void updateProduct(SellerCenterCreateVo sellerCenterCreateVo, Map productImg) {
        System.out.println(sellerCenterCreateVo);
        //product_tb 수정
//        HashMap<String,Object> productTb = new HashMap<>();
//        productTb.put("id", sellerCenterCreateVo.getId());
//        productTb.put("marketName", sellerCenterCreateVo.getMarket_name());
//        productTb.put("category", sellerCenterCreateVo.getCategory());
//        productTb.put("title", sellerCenterCreateVo.getTitle());
//        productTb.put("cont", sellerCenterCreateVo.getCont());
//        if(sellerCenterCreateVo.getEvent() == null || sellerCenterCreateVo.getEvent().equals("")) {
//            productTb.put("event", "0");
//        }else {
//            productTb.put("event", sellerCenterCreateVo.getEvent());
//        }
//        System.out.println(productTb);
//        //productDetailTb 수정
//        HashMap<String,Object> productDetailTb = new HashMap<>();
//        for(int i=0; i < sellerCenterCreateVo.getProduct().size(); i++){
//            productDetailTb.put("id", sellerCenterCreateVo.getProduct().get(i).getId());
//            productDetailTb.put("productId", sellerCenterCreateVo.getId());
//            productDetailTb.put("productName", sellerCenterCreateVo.getProduct().get(i).getProduct_name());
//            productDetailTb.put("productPrice", sellerCenterCreateVo.getProduct().get(i).getProduct_price());
//            productDetailTb.put("productDiscount", sellerCenterCreateVo.getProduct().get(i).getProduct_discount());
//            productDetailTb.put("productCount", sellerCenterCreateVo.getProduct().get(i).getProduct_count());
//            productDetailTb.put("dateStart", sellerCenterCreateVo.getProduct().get(i).getDateStart());
//            productDetailTb.put("dateEnd", sellerCenterCreateVo.getProduct().get(i).getDateEnd());
//            System.out.println(productDetailTb);
//        }
        //productImgTb 수정

        //이미지 변경 있는지 체크
        //새로운 이미지 list에 담기
        List<String> newProductImg = new ArrayList<>();
        newProductImg.add(sellerCenterCreateVo.getMainImg());
        newProductImg.addAll(sellerCenterCreateVo.getDetailImg());
        //기존 이미지 list에 담기
        List<String> existImg = new ArrayList<>();
        for(HashMap<String,Object> map : (List<HashMap<String, Object>>) productImg.get("productImg")) {
            existImg.add("http://donipop.com:3333/img/" + map.get("FILE_NAME").toString());
        }
        //기존 이미지와 새로운 이미지 비교
        List<String> deleteImg = new ArrayList<>(existImg);
        List<String> insertImg = new ArrayList<>(newProductImg);

        deleteImg.removeAll(newProductImg);
        insertImg.removeAll(existImg);
        //삭제해야 하는 이미지
        for (String img : deleteImg) {
            System.out.println(img);
            if(fileService.fileDelete(img.split("img/")[1]) == "ok"){

                if(sellerCenterDaoImpl.deleteProductImg(img.split("img/")[1].split(".")[0]) == "success"){
                    System.out.println("img_Tb삭제 성공");
                }else{
                    System.out.println("img_Tb삭제 실패");
                }

                if(fileDaoImpl.deleteFile(img.split("img/")[1].split(".")[0]) == "success"){
                    System.out.println("file_Tb삭제 성공");
                }else{
                    System.out.println("file_Tb삭제 실패");
                }
                System.out.println("이미지서버 파일 삭제 성공");
            }else{
                System.out.println("이미지서버 파일 삭제 실패");
            }
        }
        System.out.println("===================================");
        //추가해야 하는 이미지
        for (String img : insertImg) {
            if(img == sellerCenterCreateVo.getMainImg()) {
                String mainImgUpload = fileService.fileUpload(img, sellerCenterCreateVo.getUserId());
                if (mainImgUpload != "error") {
                    System.out.println("이미지서버 파일 업로드 성공");
                    sellerCenterDaoImpl.createProductImg(mainImgUpload, sellerCenterCreateVo.getId(), "1");
                } else {
                    System.out.println("이미지서버 파일 업로드 실패");
                }
            }else{
                String detailImgUpload = fileService.fileUpload(img, sellerCenterCreateVo.getUserId());
                if (detailImgUpload != "error") {
                    System.out.println("이미지서버 파일 업로드 성공");
                    sellerCenterDaoImpl.createProductImg(detailImgUpload, sellerCenterCreateVo.getId(), "0");
                } else {
                    System.out.println("이미지서버 파일 업로드 실패");
                }
            }
        }
    }
    public List<ReviewVo> getReviewListCount(HashMap<String, Object> map) {
        return sellerCenterDaoImpl.getReviewListCount(map);
    }
    public List<purchaseconfirmVo> getPurchaseConfirm(HashMap<String, String> map) {
        return sellerCenterDaoImpl.getPurchaseConfirm(map);
    }

    public List<PurchaseDetailVo> getPurchasedDetailInfo(HashMap<String, Object> map) {
        return sellerCenterDaoImpl.getPurchasedDetailInfo(map);
    }
}
