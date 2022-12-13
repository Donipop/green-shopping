package com.green.shopping.controller;

import com.green.shopping.service.SellerCenterService;
import com.green.shopping.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/sellercenter")
public class SellerCenter {
    @Autowired
    SellerCenterService sellerCenterService;

    @GetMapping("/getcategory")
    public List<CategoryVo> getCategory(@RequestParam(value = "parent_num", required = false) String parent_num) {
        if (parent_num == null || parent_num.equals("")) {
            parent_num = "-1";
        }

        return sellerCenterService.getCategoryList(parent_num);
    }
}
