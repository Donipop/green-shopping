package com.green.shopping.dao.impl;

import com.green.shopping.dao.SellerCenterDao;
import com.green.shopping.vo.CategoryVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SellerCenterDaoimpl implements SellerCenterDao {

    @Autowired
    private final SqlSession sqlSession;

    public SellerCenterDaoimpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    @Override
    public List<CategoryVo> geCategoryList(String parent_num) {
        return sqlSession.selectList("SellerCenter.getCategoryList", parent_num);
    }
}
