package com.green.shopping.dao.impl;

import com.green.shopping.dao.UserDao;
import com.green.shopping.vo.UserVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    SqlSession sqlSession;


    @Override
    public UserVo finduser_information(String user_id) {
        UserVo user_info = sqlSession.selectOne("User.finduser_information", user_id);
        return user_info;
    }

    @Override
    public void update_userinformation(Map<String, Object> map1) {
        sqlSession.update("User.update_userinformation", map1);
    }


}
