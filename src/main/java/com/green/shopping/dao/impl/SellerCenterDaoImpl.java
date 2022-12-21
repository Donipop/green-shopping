package com.green.shopping.dao.impl;

import com.green.shopping.dao.SellerCenterDao;
import com.green.shopping.vo.CategoryVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class SellerCenterDaoImpl implements SellerCenterDao {

    @Autowired
    private final SqlSession sqlSession;

    public SellerCenterDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    @Override
    public List<CategoryVo> geCategoryList(String parent_num) {
        return sqlSession.selectList("SellerCenter.getCategoryList", parent_num);
    }

    @Override
    public int createProduct(String market_Name, String category, String title, String cont, String event) {
        HashMap<String, String> createProductMap =  new HashMap<String, String>();
        createProductMap.put("market_Name", "마켓이름");
        createProductMap.put("category", category);
        createProductMap.put("title", title);
        createProductMap.put("cont", cont);
        createProductMap.put("event", "0");
        System.out.println("createProductMap : " + createProductMap);

        return sqlSession.insert("SellerCenter.createProduct",createProductMap);
    }


    @Override
    public int createProductDetail(int Product_Id, String Product_Name, String Product_Price, String Product_Discount, String Product_Count, String DateStart, String DateEnd) {
        HashMap<String, String> productDetailMap = new HashMap<>();
        productDetailMap.put("product_Id", String.valueOf(Product_Id));
        productDetailMap.put("product_Name", Product_Name);
        productDetailMap.put("product_Price", Product_Price);
        productDetailMap.put("product_Discount", Product_Discount);
        productDetailMap.put("product_Count", Product_Count);
        productDetailMap.put("dateStart", "2000-01-01");
        productDetailMap.put("dateEnd", "9999-12-31");
        System.out.println("productDetailMap : " + productDetailMap);

        return sqlSession.insert("SellerCenter.createProductDetail", productDetailMap);
    }

    @Override
    public String createProductImg(String file_Name, int productNum, String isMain) {
        //File_Name, Product_Num, IsMain
        HashMap<String, String> createProductImgMap = new HashMap<>();
        createProductImgMap.put("file_Name",file_Name);
        createProductImgMap.put("product_Num",String.valueOf(productNum));
        createProductImgMap.put("isMain",isMain);
        sqlSession.insert("SellerCenter.insertProduct_Img",createProductImgMap);
        return file_Name;
    }
}
