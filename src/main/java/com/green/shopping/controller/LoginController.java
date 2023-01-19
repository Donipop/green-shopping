package com.green.shopping.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.shopping.service.LoginService;
import com.green.shopping.vo.SellerVo;
import com.green.shopping.vo.SignUp;
import com.green.shopping.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    final HashMap<Object, Object> login_userlist = new HashMap<>();


    @Autowired
    LoginService loginService;


    @PostMapping("/login")
    @ResponseBody
    public HashMap<String, Object> loginProcess(HttpSession session, @RequestBody HashMap<String, String> map,
                                                HttpServletRequest request) throws JsonProcessingException {

        UserVo vo = loginService.login( map );

        ObjectMapper mapper = new ObjectMapper();
        String vo1 = mapper.writeValueAsString(vo);



        HashMap<String, String> map2 = mapper.readValue(vo1, HashMap.class);



        //refreshToken 만들기
        String refreshToken = UUID.randomUUID().toString();

        login_userlist.put(refreshToken, vo1);





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



        map1.put("vo", vo1);
        map1.put("returnURL", returnURL);
        map1.put("refreshToken", refreshToken);

        return map1;

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

    @PostMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpSession session,@RequestBody HashMap<String, Object> refreshTokens ) {
        String result = "로그아웃 완료";
        session.removeAttribute("login");
        Cookie cookie = new Cookie("vo", null);
        cookie.setMaxAge(0);
        String refreshToken = (String) refreshTokens.get("refreshToken");


        login_userlist.remove(refreshToken);


        return result;
    }

    @PostMapping("/sellersignup")
    public void Sellersignup(@RequestBody HashMap<String, Object> map){
        ObjectMapper objectMapper = new ObjectMapper();
        SellerVo sellerVo = objectMapper.convertValue(map, SellerVo.class);
        loginService.seller_sign_up(sellerVo);
        //파일 넘기는건 아직 안함
    }

    @PostMapping("/viewmap")
    public String tlqkf(@RequestBody HashMap<String, Object> refreshTokens){
        String qudtls = "ㄱㄱ";
        Object refreshToken = refreshTokens.get("refreshToken");
        // 로그인된 유저
        System.out.println("login_userlist = " + login_userlist);
        // 로그인된 유저의 수
        System.out.println("login_userlist.size() = " + login_userlist.size());

        String accessToken = (String) login_userlist.get(refreshToken);


        return accessToken;
    }

    @PostMapping("/refreshTokenToAccessToken")
    public HashMap<String, Object> refreshTokenToAccessToken(@RequestBody String refreshToken) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> test = mapper.readValue(refreshToken, HashMap.class);

        String RealrefreshToken = (String) test.get("refreshToken");

        if( RealrefreshToken == null) {
            return null;
        }
        else {
            String accessToken = (String) login_userlist.get(RealrefreshToken);
            if( accessToken == null) {
                return null;
            }
            else {

                HashMap<String, Object> map = mapper.readValue(accessToken, HashMap.class);
                return map;
            }
        }
    }






}
