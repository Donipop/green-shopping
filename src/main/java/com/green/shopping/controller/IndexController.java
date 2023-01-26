package com.green.shopping.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.shopping.config.SchedulConfig;
import com.green.shopping.interceptor.AuthInterceptor;
import com.green.shopping.service.IndexService;
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
    IndexService indexService;

    @GetMapping("/name")
    public List<UserVo> index() {
    List<UserVo> a = loginService.test();
        return a;
    }

    @GetMapping("/randomitemlist")
    public List<HashMap<String, Object>> randomitemlist() {
        List<HashMap<String, Object>> randomitemlist = indexService.randomitemlist();
       return randomitemlist;

    }

    @GetMapping("/recommenditemlist")
    public List<HashMap<String, Object>> recommenditemlist() {
        List<HashMap<String, Object>> recommenditemlist = indexService.recommenditemlist();
        for(int i=0; i<recommenditemlist.size(); i++) {
            HashMap<String, Object> starCount = indexService.starCount(recommenditemlist.get(i));
            if(starCount.get("star") == null) {
                recommenditemlist.get(i).put("star", 0);
                recommenditemlist.get(i).put("starCount", 0);
            } else {
                recommenditemlist.get(i).put("star", starCount.get("star"));
                recommenditemlist.get(i).put("starCount", starCount.get("starCount"));
            }
        }
       return recommenditemlist;
    }

}
