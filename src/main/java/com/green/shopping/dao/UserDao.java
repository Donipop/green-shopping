package com.green.shopping.dao;

import java.util.HashMap;
import com.green.shopping.vo.UserVo;

public interface UserDao {


    UserVo finduser_information(String user_id);
}
