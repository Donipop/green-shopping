package com.green.shopping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/sellercenter")
public class SellerSenter {

    @GetMapping("/getcategory")
    public String getcategory() {
        return "getcategory";
    }
}
