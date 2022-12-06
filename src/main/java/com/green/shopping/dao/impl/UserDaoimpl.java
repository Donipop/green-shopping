package com.green.shopping.dao.impl;

import com.green.shopping.dao.UserDao;
import com.green.shopping.vo.SignUp;
import com.green.shopping.vo.UserVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoimpl implements UserDao {

    @Autowired
    SqlSession sqlSession;

    @Override
    public List<UserVo> getList() {
        List<UserVo> a = sqlSession.selectList("User.test");
        return a;
    }

    @Override
    public void user_sign_up(SignUp signUp) {
        sqlSession.insert("User.user_sign_up", signUp);
    }
}
