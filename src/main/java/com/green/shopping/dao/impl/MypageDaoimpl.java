package com.green.shopping.dao.impl;

import com.green.shopping.dao.MypageDao;
import com.green.shopping.vo.CouponVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MypageDaoimpl implements MypageDao {
    @Autowired
    SqlSession sqlSession;
    @Override
    public List<CouponVo> Mypagecoupon() {
        return sqlSession.selectList("Mypage.Mypagecoupon");
    }
}
