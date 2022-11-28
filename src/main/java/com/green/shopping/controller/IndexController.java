package com.green.shopping.controller;

import com.green.shopping.service.Service;
import com.green.shopping.vo.SignUp;
import com.green.shopping.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api")
public class IndexController {

    @Autowired
    Service service;


    @GetMapping("/name")
    public List<UserVo> index() {
    List<UserVo> a = service.getList();
        return a;
    }

    @PostMapping("/signup")
    public String post(@RequestBody SignUp name) {
        System.out.println(name);
        return "ho";
    }


}
