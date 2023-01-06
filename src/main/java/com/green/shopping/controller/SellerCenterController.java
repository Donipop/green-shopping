package com.green.shopping.controller;

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
    SellerCenterService sellerCenterService;

    @Autowired
    FileService fileService;
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
    public void insertPostInfo(@RequestBody String RepostList) {

//        sellerCenterService.insertPostInfo(postInfo.get("invoiceNum").toString(), postInfo.get("companyName").toString(), Integer.parseInt(postInfo.get("purchaseNum").toString()));
        System.out.println(RepostList);
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
}
