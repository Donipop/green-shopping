package com.green.shopping.dao;

import com.green.shopping.vo.SignUp;
import com.green.shopping.vo.UserVo;

import java.util.List;

public interface UserDao {

    List<UserVo> getList();
    void user_sign_up(SignUp signUp);
}
