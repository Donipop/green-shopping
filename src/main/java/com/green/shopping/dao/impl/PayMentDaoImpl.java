package com.green.shopping.dao.impl;

import com.green.shopping.dao.PayMentDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PayMentDaoImpl implements PayMentDao {
    @Autowired
    SqlSession sqlSession;

}
