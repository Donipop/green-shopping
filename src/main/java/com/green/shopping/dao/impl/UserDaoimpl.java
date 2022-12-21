package com.green.shopping.dao.impl;

import com.green.shopping.dao.UserDao;
import com.green.shopping.vo.UserVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserDaoimpl implements UserDao {

    @Autowired
    SqlSession sqlSession;


    @Override
    public UserVo finduser_information(String user_id) {
        UserVo user_info = sqlSession.selectOne("Login.finduser_information", user_id);
        return user_info;
    }
}
