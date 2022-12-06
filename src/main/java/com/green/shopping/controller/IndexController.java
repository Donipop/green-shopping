package com.green.shopping.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.shopping.service.LoginService;
import com.green.shopping.vo.SignUp;
import com.green.shopping.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController("/api")
public class IndexController {

    @Autowired
    LoginService loginService;


    @GetMapping("/name")
    public List<UserVo> index() {
    List<UserVo> a = loginService.test();
        return a;
    }

    @PostMapping("/signup")
    public String post(@RequestBody SignUp name) {
        System.out.println(name);
        return "ho";
    }

    @PostMapping("/post")
    @ResponseBody
    public String test(@RequestBody HashMap<String, String> map) {

        String year = map.get("year");
        String month = map.get("month");
        String day = map.get("day");

        String brith = year + "-" + month + "-" + day;
        map.remove("year");
        map.remove("month");
        map.remove("day");

        map.put("brith", brith);

        ObjectMapper objectMapper = new ObjectMapper();
        SignUp signUp = objectMapper.convertValue(map, SignUp.class);


        loginService.user_sign_up(signUp);



        String test123 = "";

        return test123;
    }


}
