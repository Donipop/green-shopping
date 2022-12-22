package com.green.shopping.controller;

import com.green.shopping.service.FileService;
import com.green.shopping.service.SellerCenterService;
import com.green.shopping.vo.CategoryVo;
import com.green.shopping.vo.ProductVo;
import com.green.shopping.vo.SellerCenterCreateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
//        System.out.println(sellerCenterCreateVo.getMainImg());
//        fileService.fileUpload(sellerCenterCreateVo.getMainImg());
//        return "success";
    }
}
