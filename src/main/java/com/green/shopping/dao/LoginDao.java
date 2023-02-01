package com.green.shopping.dao;

import com.green.shopping.vo.SellerVo;
import com.green.shopping.vo.SignUp;
import com.green.shopping.vo.UserVo;

import java.util.HashMap;
import java.util.List;

public interface LoginDao {

    List<UserVo> getList();

    void user_sign_up(SignUp signUp);

    UserVo login(HashMap<String, String> map);

    void seller_sign_up(SellerVo sellerVo);

    String findId(HashMap<String, Object> user_NameAndTel);

    String findPassword(HashMap<String, Object> user_IdAndEmail);

    void userRoleUpdate(SellerVo sellerVo);
}
