package com.green.shopping.dao.impl;

import com.green.shopping.dao.MypageDao;
import com.green.shopping.vo.Shopping_basketVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class MypageDaoimpl implements MypageDao {

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

