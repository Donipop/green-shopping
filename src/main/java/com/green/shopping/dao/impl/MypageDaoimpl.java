package com.green.shopping.dao.impl;

import com.green.shopping.dao.MypageDao;
<<<<<<< HEAD
import com.green.shopping.vo.CouponVo;
=======
import com.green.shopping.vo.Shopping_basketVo;
>>>>>>> cf8a995c89b9f5b39850088190f0f1f9c7f23f6a
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.HashMap;
>>>>>>> cf8a995c89b9f5b39850088190f0f1f9c7f23f6a
import java.util.List;

@Repository
public class MypageDaoimpl implements MypageDao {
<<<<<<< HEAD
    @Autowired
    SqlSession sqlSession;
    @Override
    public List<CouponVo> Mypagecoupon() {
        return sqlSession.selectList("Mypage.Mypagecoupon");
    }
}
=======

    @Autowired
    private SqlSession sqlSession;


    @Override
    public List<Shopping_basketVo> user_shopping_basket(String user_id) {
        List<Shopping_basketVo> user_shopping_basket = sqlSession.selectList("Mypage.user_shopping_basket", user_id);
        return user_shopping_basket;
    }

    @Override
    public void user_shopping_basket_delete(HashMap<String, String> map) {
        sqlSession.delete("Mypage.user_shopping_basket_delete", map);
    }
}

>>>>>>> cf8a995c89b9f5b39850088190f0f1f9c7f23f6a
