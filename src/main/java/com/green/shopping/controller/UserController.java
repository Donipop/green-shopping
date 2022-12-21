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
    public String update_userinformation(@RequestBody HashMap<String, Object> map) {
        String tlqkf = "컴";
        System.out.println("map = " + map);

        return tlqkf;

    }
}
