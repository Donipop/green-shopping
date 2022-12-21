package com.green.shopping.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    LoginService loginService;


    @PostMapping("/login")
    @ResponseBody
    public HashMap<String, Object> loginProcess(HttpSession session, @RequestBody HashMap<String, String> map,
                                                HttpServletRequest request) throws JsonProcessingException {

        UserVo vo = loginService.login( map );

        ObjectMapper mapper = new ObjectMapper();
        String vo1 = mapper.writeValueAsString(vo);


        String returnURL = "";

        Cookie cookie = new Cookie("login", vo1);



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



        map1.put("vo", cookie.getValue());
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

    @PostMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpSession session, HashMap<String, Object> map) {
        String result = "로그아웃 완료";
        session.removeAttribute("login");
        Cookie cookie = new Cookie("vo", null);
        cookie.setMaxAge(0);

        return result;
    }








}
