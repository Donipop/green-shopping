package com.green.shopping.service;

import com.green.shopping.dao.UserDao;
import com.green.shopping.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public UserVo finduser_information(String user_id) {

        UserVo user_info = userDao.finduser_information(user_id);
        return user_info;
    }


    public void update_userinformation(Map<String, Object> map1) {
        userDao.update_userinformation(map1);
    }
}



