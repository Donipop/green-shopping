package com.green.shopping.dao.impl;

import com.green.shopping.dao.LoginDao;
import com.green.shopping.vo.SellerVo;
import com.green.shopping.vo.SignUp;
import com.green.shopping.vo.UserVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class LoginDaoimpl implements LoginDao {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<UserVo> getList() {
        List<UserVo> a = sqlSession.selectList("Login.test");
        return a;
    }

    @Override
    public void user_sign_up(SignUp signUp) {
        sqlSession.insert("Login.user_sign_up", signUp);
    }


    public UserVo login(HashMap<String, String> map) {
        UserVo vo = sqlSession.selectOne("Login.login", map);
        return vo;
    }

    @Override
    public void seller_sign_up(SellerVo sellerVo) {
        sqlSession.insert("Login.seller_sign_up", sellerVo);
    }
}
