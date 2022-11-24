package com.green.shopping.controller;

import com.green.shopping.vo.SignUp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController("/api")
public class IndexController {

    @GetMapping("/name")
    public String index() {
        return "배민재, baeminjae";
    }

    @PostMapping("/signup")
    public String post(@RequestBody SignUp name) {
        System.out.println(name);
        return "ho";
    }


}
