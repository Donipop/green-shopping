package com.green.shopping.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.shopping.config.SchedulConfig;
import com.green.shopping.interceptor.AuthInterceptor;
import com.green.shopping.service.LoginService;
import com.green.shopping.vo.SignUp;
import com.green.shopping.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
import java.util.*;

@RestController
public class IndexController {

    @Autowired
    LoginService loginService;
    @Autowired
    public SchedulConfig schedulConfig;

    @GetMapping("/name")
    public List<UserVo> index() {
    List<UserVo> a = loginService.test();
        return a;
    }

//    @GetMapping("/randomitemlist")
//    public List<HashMap<String, Object>> randomitemlist() {
//        List<HashMap<String, Object>> a = schedulConfig.randomitemlist();
//       return a;
//
//    }

}
