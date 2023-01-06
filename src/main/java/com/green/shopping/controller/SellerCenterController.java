package com.green.shopping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.shopping.service.FileService;
import com.green.shopping.service.SellerCenterService;
import com.green.shopping.vo.CategoryVo;
import com.green.shopping.vo.ReviewVo;
import com.green.shopping.vo.SellerCenterCreateVo;
import com.green.shopping.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

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
        System.out.println(map);
        return sellerCenterService.getReviewListCount(map);
    }
    @PostMapping("/getpurchaseconfirm")
    public List<purchaseconfirmVo> getPurchaseConfirm(@RequestParam String user_id, @RequestParam String start, @RequestParam String end) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("start", start);
        map.put("end", end);
        return sellerCenterService.getPurchaseConfirm(map);
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
    public void updateProduct(@RequestBody HashMap<String,Object> updateData) {
        SellerCenterCreateVo sellerCenterCreateVo = new SellerCenterCreateVo();
        ObjectMapper mapper = new ObjectMapper();
        sellerCenterCreateVo = mapper.convertValue(updateData.get("sellerCenterCreateVo"), SellerCenterCreateVo.class);
//        sellerCenterService.updateProduct(sellerCenterCreateVo, (HashMap<String, Object>) updateData.get("productImg"));
        System.out.println(sellerCenterCreateVo);
        System.out.println(updateData.get("productImg"));
    }

}
