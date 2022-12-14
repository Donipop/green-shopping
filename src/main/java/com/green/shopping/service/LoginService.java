package com.green.shopping.service;

import com.green.shopping.dao.LoginDao;
import com.green.shopping.vo.SignUp;
import com.green.shopping.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private LoginDao loginDao;

    public List<UserVo> test() {
        return loginDao.getList();
    }

    public void user_sign_up(SignUp signUp) {

        loginDao.user_sign_up(signUp);
    }

    public UserVo login(HashMap<String, String> map) {
        System.out.println(map);
        UserVo vo = loginDao.login(map);
        return vo;
    }
}
