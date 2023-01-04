package com.green.shopping.dao.impl;

import com.green.shopping.dao.SellerCenterDao;
import com.green.shopping.vo.CategoryVo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int createProduct(String market_name, String category, String title, String cont, String event) {
        HashMap<String, String> createProductMap =  new HashMap<String, String>();
        createProductMap.put("market_name", market_name);
        createProductMap.put("category", category);
        createProductMap.put("title", title);
        createProductMap.put("cont", cont);
        createProductMap.put("event", "0");
        //System.out.println("createProductMap : " + createProductMap);
        sqlSession.insert("SellerCenter.createProduct",createProductMap);

        return Integer.parseInt(createProductMap.get("Id"));
    }


    @Override
    public int createProductDetail(int Product_Id, String Product_Name, String Product_Price, String Product_Discount, String Product_Count, String DateStart, String DateEnd) {
        HashMap<String, String> productDetailMap = new HashMap<>();
        productDetailMap.put("product_Id", String.valueOf(Product_Id));
        productDetailMap.put("product_Name", Product_Name);
        productDetailMap.put("product_Price", Product_Price);
        productDetailMap.put("product_Discount", Product_Discount);
        productDetailMap.put("product_Count", Product_Count);
        productDetailMap.put("dateStart", DateStart);
        productDetailMap.put("dateEnd", DateEnd);

        sqlSession.insert("SellerCenter.createProductDetail", productDetailMap);
        //System.out.println("productDetailMap : " + productDetailMap);

        return Integer.parseInt(productDetailMap.get("Id"));
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

    @Override
    public List<Object> getOrderList(String marketName) {
        return sqlSession.selectList("SellerCenter.getOrderList", marketName);
    }

    @Override
    public List<Map<String,Object>> getProductIdAndTitleListByMarketName(String marketName) {
        return sqlSession.selectList("SellerCenter.getProductIdListByMarketName", marketName);
    }

    @Override
    public List<Map<String,Object>> getPurchasedListByProductId(Object productId) {
        return sqlSession.selectList("SellerCenter.getPurchasedListByProductId", productId);
    }

    @Override
    public Map<String, Object> getPostAddressById(int postNum) {
        return sqlSession.selectOne("SellerCenter.getPostAddressById", postNum);
    }

    @Override
    public List<Map<String, Object>> getOrderDetail(int orderNum) {
        return sqlSession.selectList("SellerCenter.getOrderDetail", orderNum);
    }

    @Override
    public void updateOrderStatus(int orderNum, int status) {
        HashMap<String, Integer> updateOrderStatusMap = new HashMap<>();
        updateOrderStatusMap.put("orderNum", orderNum);
        updateOrderStatusMap.put("status", status);
        sqlSession.update("SellerCenter.updateOrderStatus", updateOrderStatusMap);
    }

    @Override
    public void insertPostInfo(String invoiceNum, String companyName, int purchaseNum) {
        HashMap<String, Object> insertPostInfoMap = new HashMap<>();
        insertPostInfoMap.put("invoiceNum", invoiceNum);
        insertPostInfoMap.put("companyName", companyName);
        insertPostInfoMap.put("purchaseNum", purchaseNum);
        sqlSession.insert("SellerCenter.insertPostInfo", insertPostInfoMap);
    }
    @Override
    public List<HashMap<String, Object>> getOrderConfirm(String marketName) {
        return sqlSession.selectList("SellerCenter.getOrderConfirm", marketName);
    }

    @Override
    public List<HashMap<String, Object>> getOrderConfirmModal(int purchaseId) {
        return sqlSession.selectList("SellerCenter.getOrderConfirmModal", purchaseId);
    }

    @Override
    public List<HashMap<String, Object>> getProductTbByMarketName(String marketName) {
        return sqlSession.selectList("SellerCenter.getProductTbByMarketName", marketName);
    }

    @Override
    public HashMap<String, Object> getCategoryRoot(int num) {
        return sqlSession.selectOne("SellerCenter.getCategoryRoot", num);
    }

    @Override
    public List<HashMap<String,Object>> getProductDetailByProductId(int productId) {
        return sqlSession.selectList("SellerCenter.getProductDetailByProductId", productId);
    }

    @Override
    public List<HashMap<String, Object>> getProductImgByProductId(int productId) {
        return sqlSession.selectList("SellerCenter.getProductImgByProductId", productId);
    }
}
