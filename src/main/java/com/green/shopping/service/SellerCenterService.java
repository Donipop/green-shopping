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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SellerCenterService {
    private static final String IMG_URL = "http://donipop.com:3333/img/";
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

    public List<Map<String, Object>> getOrderList(String marketName){
        //주문정보 테이블에 있는 내 상점의 주문정보를 가져온다.
        List<PurchaselistVo> purchaselistVoList = sellerCenterDaoImpl.getPurchaseList(marketName);
//        log.info(purchaselistVoList.toString());
        //제품 테이블에 있는 내 상점의 제품정보를 가져온다.
        Map<String, Object> productVoList = sellerCenterDaoImpl.getProductIdAndTitleMapByMarketName(marketName);
//        log.info(productVoList.toString());
        List<Map<String, Object>> totalList = new ArrayList<>();
        //가지고 온 주문정보를 하나씩 돌면서 Map에 담는다.
        for (PurchaselistVo p : purchaselistVoList) {
            Map<String, Object> map = (Map<String, Object>) productVoList.get(String.valueOf(p.getProductid()));
//            log.info("p : {} a : {}",p.getProductid(),map.get("TITLE"));
            Map<String, Object> totalMap = new HashMap<>();
            totalMap.put("STATE", p.getState());
            totalMap.put("ID", p.getId());
            Calendar cal = Calendar.getInstance();
            cal.setTime(p.getTime());
            cal.add(Calendar.HOUR_OF_DAY, 9);
            totalMap.put("TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
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
    public void updateProduct(ProductUpdateVo productUpdateVo) {
        int productId = productUpdateVo.getId();
        ProductUpdateVo getProductUpdateVo = getProductUpdateVoFunc(productId);
        log.info("ProductUpdateVo : {}", productUpdateVo);
        log.info("getProductUpdateVo : {}",getProductUpdateVo);
        //상품 테이블 업데이트
        HashMap<String,Object> productTbMap = getProductTbMap(productUpdateVo, getProductUpdateVo);
        sellerCenterDaoImpl.updateProductTb(productTbMap);
        //상품 상세 테이블 업데이트
        updateProductDetailTb(productUpdateVo, getProductUpdateVo);
        //상품 이미지 테이블 업데이트
        updateImgTb(productUpdateVo, getProductUpdateVo);
    }

    private void updateImgTb(ProductUpdateVo p1, ProductUpdateVo p2) {
        List<String> p1DetailImgList = p1.getDetailImg();
        List<String> p2DetailImgList = p2.getDetailImg();

        //메인이미지 변경 확인
        if(p1.getMainImg().equals(p2.getMainImg())){
            log.info("메인이미지 변경 없음");
        }else{
            log.info("메인이미지 변경 있음");
            log.info("변경된 메인 이미지 : {}...", p1.getMainImg().substring(0,20));
            //변경된 메인 이미지 업로드
            fileService.fileUpload(p1.getMainImg(),p1.getUserId());
            //기존 메인이미지 삭제
            fileService.fileDelete(p2.getMainImg().split(IMG_URL)[1].split("\\.")[0]);
            log.info("삭제된이미지 : {}",p2.getMainImg().split(IMG_URL)[1].split("\\.")[0]);
        }
        //상세이미지 변경 확인
        List<String> addDetailImgList = p1DetailImgList.stream().filter(item -> !p2DetailImgList.contains(item)).collect(Collectors.toList());
        List<String> deleteDetailImgList = p2DetailImgList.stream().filter(item -> !p1DetailImgList.contains(item)).collect(Collectors.toList());

        //추가된 상세이미지 확인
        if(addDetailImgList.size() == 0){
            log.info("추가된 상세이미지 없음");
        }else{
            log.info("상세이미지 추가 : {}",addDetailImgList);
        }
        //삭제된 상세이미지 확인
        if(deleteDetailImgList.size() == 0) {
            log.info("삭제된 상세이미지 없음");
        }else{
            log.info("상세이미지 삭제 : {}",deleteDetailImgList);
        }
        //이미지 삭제
        for(String fileName : deleteDetailImgList){
            log.info("파일삭제 : {}", fileService.fileDelete(fileName.split(IMG_URL)[1].split("\\.")[0]));
        }
        //추가된 이미지 업로드
        for(String fileName : addDetailImgList){
            log.info("파일업로드 : {}", fileService.fileUpload(fileName,p1.getUserId()));
        }

    }

    private String updateProductDetailTb(ProductUpdateVo p1, ProductUpdateVo p2) {

        //새로 추가된 상품
        p1.getProduct().stream().forEach(item -> {
            if(item.get("id") == null){
                //상품추가
                sellerCenterDaoImpl.createProductDetail(p1.getId(), item.get("product_name").toString(),
                        item.get("product_price").toString(),item.get("product_discount").toString(), item.get("product_count").toString(),
                        item.get("dateStart").toString(), item.get("dateEnd").toString());
                log.info("새로 추가된 상품 : {}",item);
            }
        });
        //삭제된 상품
        //p1은 변경된 제품상태
        //p2은 기존 제품상태
        List<Integer> p1ProductIdList = p1.getProduct().stream().filter(item -> item.get("id") != null).map(item -> Integer.parseInt(item.get("id").toString())).collect(Collectors.toList());
        List<Integer> p2ProductIdList = p2.getProduct().stream().map(item -> Integer.parseInt(item.get("ID").toString())).collect(Collectors.toList());
        List<Integer> deleteList = p2ProductIdList.stream().filter(item -> !p1ProductIdList.contains(item)).collect(Collectors.toList());
        log.info("p1ProductIdList : {}",p1ProductIdList);
        log.info("p2ProductIdList : {}",p2ProductIdList);
        deleteList.stream().forEach(item -> {
            log.info("삭제된 상품 : {}",item);
            sellerCenterDaoImpl.updateDetailProductDeleteCheckById(item);
        });
        return "success";
    }

    private HashMap<String, Object> getProductTbMap(ProductUpdateVo p1, ProductUpdateVo p2) {
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("id",p1.getId());
        resultMap.put("marketName",p1.getMarketName());
        resultMap.put("title",p1.getTitle());
        resultMap.put("cont",p1.getCont());
        resultMap.put("event",p2.getEvent());
        resultMap.put("category",p1.getCategory());
        return resultMap;
    }

    private ProductUpdateVo getProductUpdateVoFunc(int productId) {
        Map<String, Object> productVo = sellerCenterDaoImpl.getProduct(productId);
        List<HashMap<String, Object>> productVoList = sellerCenterDaoImpl.getProductDetailByProductId(productId);
        //img 가져오기 시작
        List<HashMap<String, Object>> imgList = sellerCenterDaoImpl.getProductImgByProductId(productId);
        List<HashMap<String, Object>> fileList = new ArrayList<>();
        for (HashMap<String, Object> imgMap : imgList) {
            HashMap<String, Object> fileInfo = fileDaoImpl.getFile(imgMap.get("FILE_NAME").toString());
            HashMap<String, Object> fileMap = new HashMap<>();
            fileMap.put("FILE_PATH", IMG_URL + fileInfo.get("NAME").toString() + "." + fileInfo.get("FILE_TYPE").toString());
            fileMap.put("ISMAIN", imgMap.get("ISMAIN").toString());
            fileList.add(fileMap);
        }
        //img 가져오기 끝

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
        return productUpdateVo;
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
    public void deleteProduct(int productId) {
        sellerCenterDaoImpl.deleteProduct(productId);
    }
    public List<ReviewVo> getReviewListBySelectedId(HashMap<String, Object> map) {
        return sellerCenterDaoImpl.getReviewListBySelectedId(map);
    }

    public HashMap<String, Object> getProduct(int id) {
        return sellerCenterDaoImpl.getProduct(id);
    }
}
