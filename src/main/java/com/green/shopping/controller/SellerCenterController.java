package com.green.shopping.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.green.shopping.service.FileService;
import com.green.shopping.service.SellerCenterService;
import com.green.shopping.vo.CategoryVo;
import com.green.shopping.vo.ReviewVo;
import com.green.shopping.vo.SellerCenterCreateVo;
import com.green.shopping.vo.*;
import lombok.extern.java.Log;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sellercenter")
public class SellerCenterController {
    @Autowired
    private final SellerCenterService sellerCenterService;

    @Autowired
    private final FileService fileService;

    public SellerCenterController(SellerCenterService sellerCenterService, FileService fileService) {
        this.sellerCenterService = sellerCenterService;
        this.fileService = fileService;
    }

    @GetMapping("/getcategory")
    public List<CategoryVo> getCategory(@RequestParam(value = "parent_num", required = false) String parent_num) {
        if (parent_num == null || parent_num.equals("")) {
            parent_num = "-1";
        }
        return sellerCenterService.getCategoryList(parent_num);
    }

    @PostMapping("/create")
    public String createCategory(@RequestBody SellerCenterCreateVo sellerCenterCreateVo) {
        return sellerCenterService.create(sellerCenterCreateVo);
    }

    @GetMapping("/getorderlist")
    public List<Map<String, Object>> getOrderList(@RequestParam(value = "marketName") String marketName) {
        return sellerCenterService.getOrderList(marketName);
    }

    @GetMapping("/getpostaddress")
    public Map<String,Object> getPostAddress(@RequestParam(value = "Id") int postNum) {
        if(OptionalInt.of(postNum).isPresent()) {
            return sellerCenterService.getPostAddress(postNum);
        } else {
            return null;
        }
    }

    @GetMapping("/getorderdetail")
    public List<Map<String, Object>> getOrderDetail(@RequestParam(value = "Id") int orderNum) {
        if(OptionalInt.of(orderNum).isPresent()) {
            return sellerCenterService.getOrderDetail(orderNum);
        } else {
            return null;
        }
    }

    @PostMapping("/insertpostinfo")
    public void insertPostInfo(@RequestBody Map<String,Object> RepostList) {
        List<Map<String,Object>> repoList = (List<Map<String, Object>>) RepostList.get("RepostList");
        sellerCenterService.insertPostInfo(repoList);
    }

    @PostMapping("/updateorderstatus")
    public void updateOrderStatus(@RequestBody Map<String, Integer> map) {
        sellerCenterService.updateOrderStatus(map.get("Id"), map.get("status"));
    }
    @GetMapping("/reviewmanagement/reviewlist")
    public List<ReviewVo> getReviewListCount(@RequestParam HashMap<String, Object> map) {
        return sellerCenterService.getReviewListCount(map);
    }
    @PostMapping("/reviewmanagement/reviewlistGetBySelectedId")
    public List<ReviewVo> getReviewListBySelectedId(@RequestBody HashMap<String, Object> map) {
        return sellerCenterService.getReviewListBySelectedId(map);
    }
    @GetMapping("/reviewmanagement/reviewlistId")
    public List<Object> getReviewListId(@RequestParam HashMap<String, Object> map) {
        List<ReviewVo> reviewList = sellerCenterService.getReviewListCount(map);
        List<Object> result = new ArrayList<>();
        // id와 title값이 중복안되게 result에 넣기
        for (ReviewVo reviewVo : reviewList) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("id", reviewVo.getId());
            temp.put("title", reviewVo.getTitle());
            if (!result.contains(temp)) {
                result.add(temp);
            }
        }
        return result;
    }
    @PostMapping("/getpurchaseconfirm")
    public List<purchaseconfirmVo> getPurchaseConfirm(@RequestParam String user_id, @RequestParam String start, @RequestParam String end,
                                                      @RequestParam String selectedTitle) {
        if(selectedTitle != null && !selectedTitle.equals("") && !selectedTitle.equals("전체")) {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", user_id);
            map.put("start", start);
            map.put("end", end);
            map.put("selectedTitle", selectedTitle);
            return sellerCenterService.getPurchaseConfirmBySelectedTitle(map);
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", user_id);
            map.put("start", start);
            map.put("end", end);
            return sellerCenterService.getPurchaseConfirm(map);
        }
    }

    @PostMapping("getpurchasedetailinfo")
    public List<PurchaseDetailVo> getPurchasedDetailInfo(@RequestParam String buyerid,
                                                         @RequestParam int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("buyerid", buyerid);
        map.put("id", id);
        return sellerCenterService.getPurchasedDetailInfo(map);
    }

    @GetMapping("/getorderconfirm")
    public List<HashMap<String,Object>> getOrderConfirm(@RequestParam(value = "marketName") String marketName) {
        return sellerCenterService.getOrderConfirm(marketName);
    }

    @GetMapping("/getorderconfirmmodal")
    public List<HashMap<String,Object>> getOrderConfirmModal(@RequestParam(value = "purchaseId") int purchaseId) {
        return sellerCenterService.getOrderConfirmModal(purchaseId);
    }
    @GetMapping("/getproducttb")
    public List<HashMap<String,Object>> getProductTb(@RequestParam(value = "marketName") String marketName) {
        return sellerCenterService.getProductTbByMarketName(marketName);
    }
    @GetMapping("/getcategoryroot")
    public HashMap<String,Object> getCategoryRoot(@RequestParam(value = "num") int num) {
        return sellerCenterService.getCategoryRoot(num);
    }
    @GetMapping("/getproductdetailandimg")
    public List<HashMap<String,Object>> getProductDetailByProductId(@RequestParam(value = "productId") int productId) {
        List<HashMap<String,Object>> productDetail = sellerCenterService.getProductDetailByProductId(productId);
        List<HashMap<String,Object>> productImage = sellerCenterService.getProductImgByProductId(productId);
        //파일 이미지에 확장자 붙여주는 작업
        for(HashMap<String,Object> map : productImage) {
            HashMap<String,Object> fileMap = fileService.getFile(map.get("FILE_NAME").toString());
            map.put("FILE_NAME",fileMap.get("NAME") + "." + fileMap.get("FILE_TYPE"));
        }
        productDetail.get(0).put("PRODUCTIMG",productImage);
        return productDetail;
    }
    @PostMapping("/updateproduct")
    public void updateProduct(@RequestBody HashMap<String,Object> updateData) throws Exception {
        SellerCenterCreateVo sellerCenterCreateVo = new ObjectMapper().convertValue(updateData.get("sellerCenterCreateVo"), SellerCenterCreateVo.class);
        sellerCenterService.updateProduct(sellerCenterCreateVo, updateData);
    }
    @GetMapping("/canclecostsettle")
    public  List<HashMap<String, Object>>  cancleCostSettle(@RequestParam HashMap<String,Object> map) {

        HashMap<String, Object> putbasket = new HashMap<>();
        List<HashMap<String, Object>> SettelValue = new ArrayList<>();
        HashMap<String, Object> PurchaseConfirmCount =  sellerCenterService.PurchaseConfirmCount(map); // 구매확정 카운트
        HashMap<String, Object> beforeSettleSum =  sellerCenterService.beforeSettleSum(map); // 정산예정 금액
        HashMap<String, Object> afterSettleSum =  sellerCenterService.afterSettleSum(map); // 정산된 금액
        if(beforeSettleSum == null) {
            putbasket.put("beforeSettleSum",0);
        } else {
            putbasket.put("beforeSettleSum",beforeSettleSum.get("BEFORESETTLESUM"));
        }
        if(afterSettleSum == null) {
            putbasket.put("afterSettleSum",0);
        } else {
            putbasket.put("afterSettleSum",afterSettleSum.get("AFTERSETTLESUM"));
        }
        putbasket.put("count",PurchaseConfirmCount.get("COUNT"));
        SettelValue.add(putbasket);
        return SettelValue;
    }

    @GetMapping("/salesstatus")
    public List<Integer> salesStatus(@RequestParam HashMap<String,Object> map) {
        List<Integer> salesStatus = sellerCenterService.salesStatus (map);
        return sellerCenterService.salesStatus(map);

    }


    @GetMapping("/getmarketnamelist")
    public List<String> getMarketNameList(@RequestParam String user_id) {
        List<String> MarketNameList = sellerCenterService.getMarketNameList(user_id);

        return MarketNameList;
    }

    @PostMapping("/getsellerinfo")
    public List<Object> getSellerInfo(@RequestParam String user_id) {
        List<Object> SellerInfo = sellerCenterService.getSellerInfo(user_id);
        return SellerInfo;
    }

    @PostMapping("/getbeforesettlement")
    public List<Object> getBeforeSettlement(@RequestParam String user_id, @RequestParam String market_name) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("market_name", market_name);
        List<Object> BeforeSettlement = sellerCenterService.getBeforeSettlement(map);
        return BeforeSettlement;
    }

    @PostMapping("/settleup")
    public int settleUp(@RequestBody HashMap<String, Object> DetailInfo) {
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) DetailInfo.get("DetailInfo");
        String idlist = (String) map.get("id");
        String[] id = idlist.split(",");
        List realidlist = new ArrayList();                      // id리스트
        for (int i = 0; i < id.length; i++) {
            realidlist.add(id[i]);
        }



        String user_id = (String) map.get("user_id");           //판매자 아이디
        String market_name = (String) map.get("market_name");   //마켓명
        int totalprice = (int) map.get("totalprice");           //총 결제금액
        long bank_account = (long) map.get("bank_account");       //계좌번호
        String bank_name = (String) map.get("bank_name");       //은행명
        String bank_accountowner = (String) map.get("bank_accountowner"); //예금주
        String format_today = (String) map.get("format_today"); //정산시간 yyyy-mm-dd hh:mi:ss


        int error1 = 0;
        int error2 = 0;
        int error3 = 0;
        int checkall = 0;


        // 주문내역 테이블로 가서 주문번호를 조회 후 settlecheck를 1로 변경한다 ///////////////////////////////

        try {
            for(int i=0; i<realidlist.size(); i++) {
                int number = Integer.parseInt(realidlist.get(i).toString());
                sellerCenterService.updateSettleCheck(number);
            }
        } catch (Exception e) {
            e.printStackTrace();
            error1 = 1;
        }



        // seller_Tb로 가서 allmoney = allmoney + 정산금액 하기 ////////////////////////////////////
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("user_id", user_id);
        map2.put("market_name", market_name);
        map2.put("totalprice", totalprice);

        try {
            sellerCenterService.updateAllMoney(map2);
        } catch (Exception e) {
            e.printStackTrace();
            error2 = 1;
        }


        // 정산내역 테이블에 insert 하기 /////////////////////////////////////////////////////////////
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("user_id", user_id);
        map3.put("market_name", market_name);
        map3.put("totalprice", totalprice);
        map3.put("bank_account", bank_account);
        map3.put("bank_name", bank_name);
        map3.put("bank_accountowner", bank_accountowner);
        map3.put("format_today", format_today);
        map3.put("idlist", idlist);

        try {
            sellerCenterService.insertSettlement(map3);
        } catch (Exception e) {
            e.printStackTrace();
            error3 = 1;
        }
        /////////////////////////////////////////////////////////////////////////////////////////

        if(error1 == 1 || error2 == 1 || error3 == 1) {
            checkall = 0;
        } else {
            checkall = 1;
        }



        return checkall;
    }

    @PostMapping("/getalreadysettlement")
    public List<AlreadySettlementVo> getAlreadySettlement(@RequestParam String user_id, @RequestParam String start,
                                                          @RequestParam String end, String OrderBy) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("start", start);
        map.put("end", end);
        map.put("OrderBy", OrderBy);
        List<AlreadySettlementVo> AlreadySettlement = sellerCenterService.getAlreadySettlement(map);
        return AlreadySettlement;
    }
    @GetMapping("/deliverystate")
    public List<Integer> deliveryState(@RequestParam HashMap<String,Object> map) {
        List<Integer> deliveryState = sellerCenterService.deliveryState(map);
        return deliveryState;
    }
    @PostMapping("/addShoppingBasket")
    public void addShoppingBasket(@RequestBody HashMap<Object, Object> addShoppingBasketInfo) {
        List<Integer> countList = (List<Integer>) addShoppingBasketInfo.get("countList");
        List<Integer> productDetailIdList = (List<Integer>) addShoppingBasketInfo.get("productDetailIdList");
        String user_id = (String) addShoppingBasketInfo.get("user_id");
        List<Integer> AlreadyCountList = sellerCenterService.AlreadyCountList(user_id); // 장바구니에 등록된 count
        List<Integer> AlreadyProductDetailIdList = sellerCenterService.AlreadyProductDetailIdList(user_id); // 장바구니에 등록된 productDetailId
        HashMap<Object, Object> yetAddShoppingBasketInfo = new HashMap<>();
        HashMap<Object, Object> alreadyAddShoppingBasketInfo = new HashMap<>();
        for( int v = 0; v < productDetailIdList.size(); v++ ) {
            HashMap<String, Object> map = new HashMap<>();
            if(AlreadyProductDetailIdList.contains(productDetailIdList.get(v))) {
                int index = AlreadyProductDetailIdList.indexOf(productDetailIdList.get(v));
                int count = AlreadyCountList.get(index) + countList.get(v);
                map.put("count", count);
                map.put("productDetailId", productDetailIdList.get(v));
                map.put("user_id", user_id);
                alreadyAddShoppingBasketInfo.put(v, map);
            } else {
                map.put("count", countList.get(v));
                map.put("productDetailId", productDetailIdList.get(v));
                map.put("user_id", user_id);
                yetAddShoppingBasketInfo.put(v, map);
            }
        }
        List<Integer> keyList = new ArrayList<>();
        for(Object key : alreadyAddShoppingBasketInfo.keySet()) {
            keyList.add((Integer) key);
        }
        for(Object key : yetAddShoppingBasketInfo.keySet()) {
            keyList.add((Integer) key);
        }
        if(alreadyAddShoppingBasketInfo.size() == 0) {
            for(int i = 0; i < keyList.size(); i++) {
                HashMap<String, Object> map = (HashMap<String, Object>) yetAddShoppingBasketInfo.get(i);
                sellerCenterService.addShoppingBasket(map);
            }
        } else if(yetAddShoppingBasketInfo.size() == 0) {
            for(int i = 0; i < keyList.size(); i++) {
                HashMap<String, Object> map = (HashMap<String, Object>) alreadyAddShoppingBasketInfo.get(i);
                sellerCenterService.updateShoppingBasket(map);
            }
        } else {
            for(int i = 0; i < keyList.size(); i++) {
                HashMap<String, Object> map = (HashMap<String, Object>) alreadyAddShoppingBasketInfo.get(i);
                if(map == null) {
                    continue;
                }
                sellerCenterService.updateShoppingBasket(map);
            }
            for(int i = 0; i < keyList.size(); i++) {
                HashMap<String, Object> map = (HashMap<String, Object>) yetAddShoppingBasketInfo.get(i);
                if(map == null) {
                    continue;
                }
                sellerCenterService.addShoppingBasket(map);
            }
        }


    }
    @GetMapping("/getmarketNamebySellerid")
    public String getMarketNamebySellerid(@RequestParam String user_id){
        String marketName = sellerCenterService.getMarketNamebySellerid(user_id);
        return marketName;
    }
    @PostMapping("/deleteProduct")
    public void deleteProduct(@RequestBody Map productId){
        sellerCenterService.deleteProduct(Integer.parseInt(productId.get("productId").toString()));
    }

}
