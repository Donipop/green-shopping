package com.green.shopping.service;

import com.green.shopping.dao.LoginDao;
import com.green.shopping.vo.SellerVo;
import com.green.shopping.dao.impl.UserDaoImpl;
import com.green.shopping.vo.SignUp;
import com.green.shopping.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LoginService {


    @Autowired
    private LoginDao loginDao;

    private UserDaoImpl userDaoimpl;

    public List<UserVo> test() {
        return loginDao.getList();
    }

    public void user_sign_up(SignUp signUp) {

        loginDao.user_sign_up(signUp);
    }

    public UserVo login(HashMap<String, String> map) {
        UserVo vo = loginDao.login(map);
        return vo;
    }

    public void seller_sign_up(SellerVo sellerVo) {
        loginDao.seller_sign_up(sellerVo);
    }

    public String findId(HashMap<String, Object> user_NameAndTel) {
        return loginDao.findId(user_NameAndTel);
    }

    public String findPassword(HashMap<String, Object> user_IdAndEmail) {
        return loginDao.findPassword(user_IdAndEmail);
    }

    public void AddPostAddress(HashMap<String, Object> yetAddPostAddress) {
        loginDao.AddPostAddress(yetAddPostAddress);
    }

    public void userRoleUpdate(SellerVo sellerVo) {
        loginDao.userRoleUpdate(sellerVo);
    }

    public void marketUpdate(HashMap<String, Object> marketBasket) {
        loginDao.marketUpdate(marketBasket);
    }

    public int checkDuplicateId(String user_id) {
        return loginDao.checkDuplicateId(user_id);
    }
    public int checkDuplicateNick(String user_nick) {
        return loginDao.checkDuplicateNick(user_nick);
    }

    public int checkDuplicateNameAndTel(HashMap<String, Object> user_NameAndTel) {
        return loginDao.checkDuplicateNameAndTel(user_NameAndTel);
    }
}
