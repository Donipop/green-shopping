package com.green.shopping.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PayMentController {

    @PostMapping("/purchase")
    public String purchase() {

        return "success";
    }
}
