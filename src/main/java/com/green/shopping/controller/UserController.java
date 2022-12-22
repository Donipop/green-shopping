package com.green.shopping.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.green.shopping.service.UserService;
import com.green.shopping.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mypage")
public class UserController {


    @Autowired
    UserService userService;

    @PostMapping("/myinformation")
    @ResponseBody
    public UserVo user_information(@RequestBody HashMap<String, String> map,
                                   HttpSession session, HttpServletRequest request) throws JsonProcessingException {

        String userinfo = map.get("user_info");

        Map<String,Object> map1 = new ObjectMapper().readValue(userinfo, HashMap.class);
        String user_id = (String) map1.get("user_id");

        UserVo user_info = userService.finduser_information(user_id);


        return user_info;
    }

    @PostMapping("/update_userinformation")
    public HashMap update_userinformation(@RequestBody HashMap<String, String> map) throws JsonProcessingException {
        String tlqkf = "컴";

        String account = map.get("account");
        Map<String, Object> map1 = new ObjectMapper().readValue(account, HashMap.class);
        String user_id = (String) map1.get("user_id");

        //회원정보 업데이트
        userService.update_userinformation(map1);

        //회원정보를 업데이트 한 후 다시 유저정보를 들고오기(세션 및 쿠키 정보 갱신)
        UserVo vo = userService.finduser_information(user_id);

        HashMap<String, Object> map2 = new HashMap<>();

        map2.put("vo", vo);


        return map2;

    }
}
