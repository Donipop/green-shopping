package com.green.shopping.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.shopping.service.FileService;
import com.green.shopping.service.LoginService;
import com.green.shopping.vo.SellerVo;
import com.green.shopping.vo.SignUp;
import com.green.shopping.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    final HashMap<Object, Object> login_userlist = new HashMap<>();


    @Autowired
    LoginService loginService;

    private final FileService fileService;

    public LoginController(FileService fileService) {
        this.fileService = fileService;
    }


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
        String user_id = map.get("username");
        String name = map.get("name");
        String address = map.get("address");
        String tel = map.get("tel");
        HashMap<String, Object> yetAddPostAddress = new HashMap<>();
        yetAddPostAddress.put("user_id", user_id);
        yetAddPostAddress.put("name", name);
        yetAddPostAddress.put("address", address);
        yetAddPostAddress.put("tel", tel);
        yetAddPostAddress.put("cont", "부재시 문앞에 놔주세요");
        int SignUpCheck = 0;
        int AddPostAddressCheck = 0;
        try {
            loginService.user_sign_up(signUp);
            SignUpCheck = 1;
        } catch(Exception e) {
            System.out.println(e);
        }
        if(SignUpCheck == 1) {
            try {
                loginService.AddPostAddress(yetAddPostAddress);
                AddPostAddressCheck = 1;
            } catch(Exception e) {
                System.out.println(e);
            }
        }
        if(AddPostAddressCheck == 1) {
            return "회원가입 성공";
        } else {
            return "회원가입 실패";
        }
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
    public void Sellersignup(@RequestBody SellerVo sellerVo){
        loginService.seller_sign_up(sellerVo);
        loginService.userRoleUpdate(sellerVo);
        String businessImg = fileService.fileUpload(sellerVo.getMainImg(), "test");
        String accountImg = fileService.fileUpload(sellerVo.getMainImg(), "test");

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
    @PostMapping("/findId")
    public String findId(@RequestParam String user_name, @RequestParam String user_tel) {
        HashMap<String, Object> user_NameAndTel = new HashMap<>();
        user_NameAndTel.put("user_name", user_name);
        user_NameAndTel.put("user_tel", user_tel);
        String user_id = loginService.findId(user_NameAndTel);
        return user_id;
    }
    @PostMapping("/findPassword")
    public String findPassword(@RequestParam String user_id, @RequestParam String user_email) {
        HashMap<String, Object> user_IdAndEmail = new HashMap<>();
        user_IdAndEmail.put("user_id", user_id);
        user_IdAndEmail.put("user_email", user_email);
        String user_password = loginService.findPassword(user_IdAndEmail);
        return user_password;
    }





}
