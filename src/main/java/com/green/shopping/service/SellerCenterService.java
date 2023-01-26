package com.green.shopping.service;

import com.green.shopping.dao.impl.FileDaoImpl;
import com.green.shopping.dao.impl.SellerCenterDaoImpl;
import com.green.shopping.vo.CategoryVo;
import com.green.shopping.vo.ProductVo;
import com.green.shopping.vo.ReviewVo;
import com.green.shopping.vo.SellerCenterCreateVo;
import com.green.shopping.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
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

    public List<CategoryVo> getCategoryList(String parent_num) {
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
            String mainImgUpload = fileService.fileUpload(sellerCenterCreateVo.getMainImg(), sellerCenterCreateVo.getUserId());
            //File_Tb에 업로드한 파일의 Id받아서 Product_Img_Tb에 저장
            if (mainImgUpload != "error") {
                sellerCenterDaoImpl.createProductImg(mainImgUpload, productCreate_and_Num, "1");
            }
            //상세 이미지 업로드
            for (int i = 0; i < sellerCenterCreateVo.getDetailImg().size(); i++) {
                if (sellerCenterCreateVo.getDetailImg().get(i).length() > 0) {
                    String detailImgUpload = fileService.fileUpload(sellerCenterCreateVo.getDetailImg().get(i), sellerCenterCreateVo.getUserId());
                    if (detailImgUpload != "error") {
                        sellerCenterDaoImpl.createProductImg(detailImgUpload, productCreate_and_Num, "0");
                    }
                }

            }
        }
        return "success";
    }

    public List<Map<String, Object>> getOrderList(String marketName) {
        //주문정보 테이블에 있는 내 상점의 주문정보를 가져온다.
        List<PurchaselistVo> purchaselistVoList = sellerCenterDaoImpl.getPurchaseList(marketName);
//        log.info(purchaselistVoList.toString());
        //제품 테이블에 있는 내 상점의 제품정보를 가져온다.
        Map<String, Object> productVoList = sellerCenterDaoImpl.getProductIdAndTitleMapByMarketName(marketName);
//        log.info(productVoList.toString());
        List<Map<String, Object>> totalList = new ArrayList<>();
        //가지고 온 주문정보를 하나씩 돌면서 Map에 담는다.
        for (PurchaselistVo p : purchaselistVoList) {
            ;
            Map<String, Object> map = (Map<String, Object>) productVoList.get(String.valueOf(p.getProductid()));
//            log.info("p : {} a : {}",p.getProductid(),map.get("TITLE"));
            Map<String, Object> totalMap = new HashMap<>();
            totalMap.put("STATE", p.getState());
            totalMap.put("ID", p.getId());
            totalMap.put("TIME", p.getTime());
            totalMap.put("product_Title", map.get("TITLE"));
            totalMap.put("PRODUCTID", p.getProductid());
            totalMap.put("POSTADDRESSID", p.getPostaddressid());
            totalList.add(totalMap);
        }
//        log.info(productVoList.get("700"));
        return totalList;
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

    public void insertPostInfo(List<Map<String, Object>> postInfoList) {
        for (int i = 0; i < postInfoList.size(); i++) {
            //post_Tb에 추가
            sellerCenterDaoImpl.insertPostInfo(postInfoList.get(i).get("invoiceNum").toString(), postInfoList.get(i).get("companyName").toString(), Integer.parseInt(postInfoList.get(i).get("purchaseNum").toString()));
            sellerCenterDaoImpl.updateOrderStatus(Integer.parseInt(postInfoList.get(i).get("purchaseNum").toString()), 3);
        }
    }

    public List<HashMap<String, Object>> getOrderConfirm(String marketName) {
        return sellerCenterDaoImpl.getOrderConfirm(marketName);
    }

    public List<HashMap<String, Object>> getOrderConfirmModal(int purchaseNum) {
        return sellerCenterDaoImpl.getOrderConfirmModal(purchaseNum);
    }

    public List<HashMap<String, Object>> getProductTbByMarketName(String marketName) {
        return sellerCenterDaoImpl.getProductTbByMarketName(marketName);
    }

    public HashMap<String, Object> getCategoryRoot(int num) {
        return sellerCenterDaoImpl.getCategoryRoot(num);
    }

    public List<HashMap<String, Object>> getProductDetailByProductId(int productId) {
        return sellerCenterDaoImpl.getProductDetailByProductId(productId);
    }

    public List<HashMap<String, Object>> getProductImgByProductId(int productId) {
        return sellerCenterDaoImpl.getProductImgByProductId(productId);
    }

    @Transactional
    public void updateProduct(SellerCenterCreateVo sellerCenterCreateVo, Map productMap) {
//        System.out.println(sellerCenterCreateVo);
//        product_tb 수정
        HashMap<String, Object> productTb = new HashMap<>();
        productTb.put("id", sellerCenterCreateVo.getId());
        productTb.put("marketName", sellerCenterCreateVo.getMarket_name());
        productTb.put("category", sellerCenterCreateVo.getCategory());
        productTb.put("title", sellerCenterCreateVo.getTitle());
        productTb.put("cont", sellerCenterCreateVo.getCont());
        if (sellerCenterCreateVo.getEvent() == null || sellerCenterCreateVo.getEvent().equals("")) {
            productTb.put("event", "0");
        } else {
            productTb.put("event", sellerCenterCreateVo.getEvent());
        }
        sellerCenterDaoImpl.updateProductTb(productTb);

        List<String> deleteProductIdList = List.of(String.valueOf(productMap.get("deleteProductId")).split(","));
        for (int i = 0; i < deleteProductIdList.size(); i++) {
            if (deleteProductIdList.get(i).replaceAll("[\\[\\]]", "").trim().equals("") || deleteProductIdList.get(i).replaceAll("[\\[\\]]", "").trim().equals("null"))
                continue;
//            System.out.println(deleteProductIdList.get(i).replaceAll("[\\[\\]]","").trim() + "삭제");
            sellerCenterDaoImpl.deleteProductDetailTb(deleteProductIdList.get(i).replaceAll("[\\[\\]]", "").trim());
        }
        //productDetailTb 수정
        HashMap<String, Object> productDetailTb = new HashMap<>();
        for (int i = 0; i < sellerCenterCreateVo.getProduct().size(); i++) {
//            System.out.println(sellerCenterCreateVo.getProduct().get(i));
            if (sellerCenterCreateVo.getProduct().get(i).getId() == 0) {
//                System.out.println(sellerCenterCreateVo.getProduct().get(i).getProduct_name());
                // id == 0 추가
                sellerCenterDaoImpl.createProductDetail(sellerCenterCreateVo.getId(),
                        sellerCenterCreateVo.getProduct().get(i).getProduct_name(),
                        sellerCenterCreateVo.getProduct().get(i).getProduct_price(),
                        sellerCenterCreateVo.getProduct().get(i).getProduct_discount(),
                        sellerCenterCreateVo.getProduct().get(i).getProduct_count(),
                        sellerCenterCreateVo.getProduct().get(i).getDateStart(),
                        sellerCenterCreateVo.getProduct().get(i).getDateEnd());
            } else {
                //id != 0 업데이트
                productDetailTb.put("id", sellerCenterCreateVo.getProduct().get(i).getId());
                productDetailTb.put("productId", sellerCenterCreateVo.getId());
                productDetailTb.put("productName", sellerCenterCreateVo.getProduct().get(i).getProduct_name());
                productDetailTb.put("productPrice", sellerCenterCreateVo.getProduct().get(i).getProduct_price());
                productDetailTb.put("productDiscount", sellerCenterCreateVo.getProduct().get(i).getProduct_discount());
                productDetailTb.put("productCount", sellerCenterCreateVo.getProduct().get(i).getProduct_count());
                productDetailTb.put("dateStart", sellerCenterCreateVo.getProduct().get(i).getDateStart());
                productDetailTb.put("dateEnd", sellerCenterCreateVo.getProduct().get(i).getDateEnd());
//                sellerCenterDaoImpl.updateProductDetailTb(productDetailTb);
            }
//            System.out.println(productDetailTb);
        }

//        productImgTb 수정

        ////////////////////////////////////////////////////////

        //이미지 변경 있는지 체크
        //새로운 이미지 list에 담기
        List<String> newProductImg = new ArrayList<>();
        newProductImg.add(sellerCenterCreateVo.getMainImg());
        newProductImg.addAll(sellerCenterCreateVo.getDetailImg());
        //기존 이미지 list에 담기
        List<String> existImg = new ArrayList<>();
        for (HashMap<String, Object> map : (List<HashMap<String, Object>>) productMap.get("productImg")) {
            existImg.add("http://donipop.com:3333/img/" + map.get("FILE_NAME").toString());
        }
        //기존 이미지와 새로운 이미지 비교
        List<String> deleteImg = new ArrayList<>(existImg);
        List<String> insertImg = new ArrayList<>(newProductImg);

        deleteImg.removeAll(newProductImg);
        insertImg.removeAll(existImg);
        //삭제해야 하는 이미지
        for (String img : deleteImg) {
            String imageServer = fileService.fileDelete(img.split("img/")[1]);
//            System.out.println("img/1 : " + img.split("img/")[1]);
//            System.out.println("img/2 : " + img.split("img/")[1].split("\\.")[0]);
            if (imageServer.equals("ok")) {
                String imageServerRes = sellerCenterDaoImpl.deleteProductImg(img.split("img/")[1].split("\\.")[0]);
                if (imageServerRes == "success") {
//                    System.out.println("img_Tb삭제 성공");
                } else {
//                    System.out.println("img_Tb삭제 실패");
                }

                if (fileDaoImpl.deleteFile(img.split("img/")[1].split("\\.")[0]) == "success") {
//                    System.out.println("file_Tb삭제 성공");
                } else {
//                    System.out.println("file_Tb삭제 실패");
                }
//                System.out.println("이미지서버 파일 삭제 성공");
            } else {
//                System.out.println("이미지서버 파일 삭제 실패");
            }
        }
//        System.out.println("===================================");
        //추가해야 하는 이미지
        for (String img : insertImg) {
            if(img.equals("")){ continue; }
            String imgSrc = img.split("___")[0];
//            System.out.println("imgSrc : " + imgSrc);
            String isMain = img.split("___")[1];
//            System.out.println("isMain : " + isMain);
            if (imgSrc == sellerCenterCreateVo.getMainImg()) {
                String mainImgUpload = fileService.fileUpload(imgSrc, sellerCenterCreateVo.getUserId());
                if (mainImgUpload != "error") {
//                    System.out.println("이미지서버 파일 업로드 성공");
                    sellerCenterDaoImpl.createProductImg(mainImgUpload, sellerCenterCreateVo.getId(), isMain);
                } else {
//                    System.out.println("이미지서버 파일 업로드 실패");
                }
            } else {
                String detailImgUpload = fileService.fileUpload(imgSrc, sellerCenterCreateVo.getUserId());
                if (detailImgUpload != "error") {
//                    System.out.println("이미지서버 파일 업로드 성공" + detailImgUpload);
//                    System.out.println(sellerCenterCreateVo.getId());
                    sellerCenterDaoImpl.createProductImg(detailImgUpload, sellerCenterCreateVo.getId(), isMain);
                } else {
//                    System.out.println("이미지서버 파일 업로드 실패");
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

    public List<String> getMarketNameList(String user_id) {
        List<String> MarketNameList = sellerCenterDaoImpl.getMarketNameList(user_id);
        return MarketNameList;
    }

    public List<Object> getSellerInfo(String user_id) {
        List<Object> SellerInfo = sellerCenterDaoImpl.getSellerInfo(user_id);
        return SellerInfo;
    }

    public List<Object> getBeforeSettlement(HashMap<String, Object> map) {
        List<Object> BeforeSettlement = sellerCenterDaoImpl.getBeforeSettlement(map);
        return BeforeSettlement;
    }

    public void updateSettleCheck(int number) {
        sellerCenterDaoImpl.updateSettleCheck(number);
    }

    public void updateAllMoney(HashMap<String, Object> map2) {
        sellerCenterDaoImpl.updateAllMoney(map2);
    }

    public void insertSettlement(HashMap<String, Object> map3) {
        sellerCenterDaoImpl.insertSettlement(map3);
    }

    public List<AlreadySettlementVo> getAlreadySettlement(HashMap<String, Object> map) {
        List<AlreadySettlementVo> AlreadySettlement = sellerCenterDaoImpl.getAlreadySettlement(map);
        return AlreadySettlement;
    }

    public HashMap<String, Object> PurchaseConfirmCount(HashMap<String, Object> map) {
        return sellerCenterDaoImpl.PurchaseConfirmCount(map);
    }

    public HashMap<String, Object> beforeSettleSum(HashMap<String, Object> map) {
        return sellerCenterDaoImpl.beforeSettleSum(map);
    }

    public HashMap<String, Object> afterSettleSum(HashMap<String, Object> map) {
        return sellerCenterDaoImpl.afterSettleSum(map);
    }

    public List<Integer> salesStatus(HashMap<String, Object> map) {
        return sellerCenterDaoImpl.salesStatus(map);
    }

    public List<Integer> deliveryState(HashMap<String, Object> map) {
        return sellerCenterDaoImpl.deliveryState(map);
    }

    public void addShoppingBasket(HashMap<String, Object> map) {
        sellerCenterDaoImpl.addShoppingBasket(map);
    }


    public List<Integer> AlreadyCountList(String user_id) {
        return sellerCenterDaoImpl.AlreadyCountList(user_id);
    }

    public List<Integer> AlreadyProductDetailIdList(String user_id) {
        return sellerCenterDaoImpl.AlreadyProductDetailIdList(user_id);
    }

    public void updateShoppingBasket(HashMap<String, Object> alreadyAddShoppingBasketInfo) {
        sellerCenterDaoImpl.updateShoppingBasket(alreadyAddShoppingBasketInfo);
    }

    public String getMarketNamebySellerid(String user_id) {
        return sellerCenterDaoImpl.getMarketNamebySellerid(user_id);
    }
}
