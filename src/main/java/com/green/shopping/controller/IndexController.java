package com.green.shopping.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.shopping.interceptor.AuthInterceptor;
import com.green.shopping.service.LoginService;
import com.green.shopping.vo.SignUp;
import com.green.shopping.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/api")
public class IndexController {

    @Autowired
    LoginService loginService;


    @PostMapping("/login")
    @ResponseBody
    public HashMap<String, Object> loginProcess(HttpSession session, @RequestBody HashMap<String, String> map) {
        UserVo vo = loginService.login( map );
        String returnURL = "";
        System.out.println(session);

        HashMap<String, Object> map1 = new HashMap<>();

        if ( session.getAttribute("login") != null) {
            session.removeAttribute("login");
        }

        if ( vo != null) {
            session.setAttribute("login", vo);
            returnURL = "/";
        } else {
            returnURL = "/login";
        }


        map1.put("vo", vo);
        map1.put("returnURL", returnURL);


        return map1;

    }


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

        System.out.println("signUp = " + signUp);
        loginService.user_sign_up(signUp);

        String test123 = "";

        return test123;
    }

    @PostMapping("/test123")
    @ResponseBody
    public String test123(HttpServletRequest request) {
        String result = "실패";

        Cookie[] myCookies = request.getCookies();

        String decodeData = "";

        for( int i = 0; i < myCookies.length; i++) {
            System.out.println(i + "번째 쿠키 이름: " + myCookies[i].getName() );
            System.out.println(i + "번째 쿠키 값: " + myCookies[i].getValue());

            try {
                decodeData = URLDecoder.decode(myCookies[i].getValue(), "UTF-8");
                System.out.println(i + "번째 쿠기 값 디코딩 결과 : " + decodeData);


            } catch(Exception e) {
                e.printStackTrace();
            }


        }




        return result;
    }








}
