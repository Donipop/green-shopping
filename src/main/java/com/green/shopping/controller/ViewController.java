package com.green.shopping.controller;

import com.green.shopping.service.ViewService;
import com.green.shopping.vo.ProductVo;
import com.green.shopping.vo.SellerCenterCreateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private final ViewService viewService;

    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping("/product")
    public SellerCenterCreateVo product(@RequestParam int product_num) {
        //product_num == 숫자가 아닐경우 에러처리
        if (OptionalInt.of(product_num).isPresent()) {
            return viewService.getProduct(product_num);
        } else {
            return null;
        }
    }
}
