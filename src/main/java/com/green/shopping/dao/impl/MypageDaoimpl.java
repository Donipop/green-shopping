package com.green.shopping.dao.impl;

import com.green.shopping.dao.MypageDao;
import com.green.shopping.vo.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MypageDaoimpl implements MypageDao {
    @Autowired
    private final SqlSession sqlSession;
    public MypageDaoimpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    @Override
    public List<CouponVo> Mypagecoupon() {
        return sqlSession.selectList("Mypage.Mypagecoupon");
    }
    @Override
    public List<Shopping_basketVo> user_shopping_basket(String user_id) {
        List<Shopping_basketVo> user_shopping_basket = sqlSession.selectList("Mypage.user_shopping_basket", user_id);
        return user_shopping_basket;
    }

    @Override
    public void user_shopping_basket_delete(HashMap<String,Object> map) {
        sqlSession.delete("Mypage.user_shopping_basket_delete", map);
    }

    @Override
    public int check_duplicate_nick(String user_nick) {
        return sqlSession.selectOne("Mypage.check_duplicate_nick", user_nick);
    }

    @Override
    public void myinfoUpdate(HashMap<String, Object> myinfo2) {
        sqlSession.update("Mypage.myinfoUpdate", myinfo2);
    }
    @Override
    public int countBasket(String user_id) {
        return sqlSession.selectOne("Mypage.countBasket", user_id);
    }

    @Override
    public String getmainImageByproductId(int productid) {
        return sqlSession.selectOne("Mypage.getmainImageByproductId", productid);
    }

    @Override
    public String getproductNameByproductId(int productid) {
        return sqlSession.selectOne("Mypage.getproductNameByproductId", productid);
    }

    @Override
    public HashMap<String, Object> getProductImgByProductId(int productid) {

        return sqlSession.selectOne("Mypage.getProductImgByProductId", productid);
    }

    @Override
    public HashMap<String, Object> getFile(String file_name) {
        return sqlSession.selectOne("Mypage.getFile", file_name);
    }

    @Override
    public void MyPurchaseConfirm(HashMap<String, Object> map) {
        sqlSession.update("Mypage.MyPurchaseConfirm", map);
    }

    @Override
    public List<ReviewVo> myreview(String user_id) {

        return sqlSession.selectList("Mypage.myreview", user_id);
    }

    @Override
    public List<MyPurchaseInquiryVo> mypruchaseinquiry(String user_id) {
        return sqlSession.selectList("Mypage.mypruchaseinquiry", user_id);
    }

    @Override
    public TestpostVo invoiceNumberGet(long invoicenumber) {
        return sqlSession.selectOne("Mypage.invoiceNumberGet", invoicenumber);
    }
}
