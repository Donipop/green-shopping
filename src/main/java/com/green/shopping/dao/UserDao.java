package com.green.shopping.dao;

import java.util.HashMap;
import java.util.Map;

import com.green.shopping.vo.UserVo;

public interface UserDao {


    UserVo finduser_information(String user_id);

    void update_userinformation(Map<String, Object> map1);
}
