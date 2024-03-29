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

    @Override
    public String findId(HashMap<String, Object> user_NameAndTel) {
        return sqlSession.selectOne("Login.findId", user_NameAndTel);
    }

    @Override
    public String findPassword(HashMap<String, Object> user_IdAndEmail) {
        return sqlSession.selectOne("Login.findPassword", user_IdAndEmail);
    }

    @Override
    public void AddPostAddress(HashMap<String, Object> yetAddPostAddress) {
        sqlSession.insert("Login.AddPostAddress", yetAddPostAddress);
    }

    @Override
    public void userRoleUpdate(SellerVo sellerVo) {
       sqlSession.update("Login.userRoleUpdate", sellerVo);
    }

    @Override
    public void marketUpdate(HashMap<String, Object> marketBasket) {
        sqlSession.update("Login.marketUpdate", marketBasket);
    }

    @Override
    public int checkDuplicateId(String user_id) {
        return sqlSession.selectOne("Login.checkDuplicateId", user_id);
    }

    @Override
    public int checkDuplicateNick(String user_nick) {
        return sqlSession.selectOne("Login.checkDuplicateNick", user_nick);
    }

    @Override
    public int checkDuplicateNameAndTel(HashMap<String, Object> user_NameAndTel) {
        return sqlSession.selectOne("Login.checkDuplicateNameAndTel", user_NameAndTel);
    }

}
