package com.green.shopping.dao.impl;

import com.green.shopping.dao.Dao;
import com.green.shopping.vo.UserVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Daoimpl implements Dao {

    @Autowired
    SqlSession sqlSession;

    @Override
    public List<UserVo> getList() {
        List<UserVo> a = sqlSession.selectList("User.test");
        return a;
    }
}
