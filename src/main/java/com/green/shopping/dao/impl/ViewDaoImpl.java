package com.green.shopping.dao.impl;

import com.green.shopping.dao.ViewDao;
import com.green.shopping.vo.ProductImgVo;
import com.green.shopping.vo.ProductVo;
import com.green.shopping.vo.SellerCenterCreateVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ViewDaoImpl implements ViewDao {

    @Autowired
    SqlSession sqlSession;

    @Override
    public SellerCenterCreateVo getProduct(int product_id) {
        return sqlSession.selectOne("View.getProduct", product_id);
    }

    @Override
    public List<ProductVo> getProductDetail(int product_id) {
        return sqlSession.selectList("View.getProductDetail", product_id);
    }

    @Override
    public List<ProductImgVo> getProductImg(int product_id) {
        return sqlSession.selectList("View.getProductImg", product_id);
    }

    @Override
    public String getProductImgExtension(String img_id) {
        return sqlSession.selectOne("View.getProductImgExtension", img_id);
    }
}
