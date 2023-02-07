package com.green.shopping.dao.impl;

import com.green.shopping.dao.ReviewDao;
import com.green.shopping.vo.QnAVo;
import com.green.shopping.vo.ReviewVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Repository
public class ReviewDaoimpl implements ReviewDao {

    @Autowired
    SqlSession sqlSession;
    @Override
    public void ReviewWrite(ReviewVo reviewVo) {
        sqlSession.insert("Review.ReviewWrite", reviewVo);
    }

    @Override
    public void QuestionWrite(HashMap<String, Object> map) {
        sqlSession.insert("Review.QnAWrite", map);
    }

    @Override
    public List<QnAVo> QuestionList(@PathVariable("page") int page) {
        return sqlSession.selectList("Review.QnAList", page);
    }

    @Override
    public QnAVo QuestionOneList(@RequestParam HashMap<String ,Object> map) {
        return sqlSession.selectOne("Review.QnAReply", map);
    }

    @Override
    public void AnswerWrite(HashMap<String, Object> map) {
        sqlSession.insert("Review.QnAreplyWrite", map);
    }

    @Override
    public List<ReviewVo> reviewList(int page) {
        return sqlSession.selectList("Review.reviewList", page);
    }

    @Override
    public void reviewDelete(HashMap<String, Object> map) {
        sqlSession.delete("Review.reviewDelete", map);
    }

    @Override
    public ReviewVo reviewUpdateForm(HashMap<String, Object> map) {
        return sqlSession.selectOne("Review.ReviewOneSelect", map);
    }

    @Override
    public List<QnAVo> QnareplyList(HashMap<String, Object> map) {
        return sqlSession.selectList("Review.QnareplyList", map);
    }

    @Override
    public void reviewUpdate(HashMap<String, Object> map) {
         sqlSession.update("Review.reviewUpdate", map);
    }

    @Override
    public void QuestionDelete(HashMap<String, Object> map) {
        sqlSession.update("Review.QuestionDelete", map);
    }

    @Override
    public void answerDelete(HashMap<String, Object> map) {
       sqlSession.delete("Review.answerDelete", map);
    }

    @Override
    public QnAVo QuestionUpdateForm(HashMap<String, Object> map) {
        return sqlSession.selectOne("Review.QnAanswerupdate", map);
    }

    @Override
    public void QuestionUpdate(HashMap<String, Object> map) {
           sqlSession.update("Review.QuestionUpdate", map);
    }

    @Override
    public void answerUpdate(HashMap<String, Object> map) {
        sqlSession.update("Review.answerUpdate", map);
    }

    @Override
    public void QuestionHardDelete(HashMap<String, Object> map) {
        sqlSession.delete("Review.QuestionHardDelete", map);
    }

    @Override
    public String getProductName(int page) {
        return sqlSession.selectOne("Review.getProductName", page);
    }

    @Override
    public String getmarketName(HashMap<String, Object> map) {
        return sqlSession.selectOne("Review.getmarketName", map);
    }

    @Override
    public String getmarketNamebyproductid(HashMap<String, Object> map) {
        return sqlSession.selectOne("Review.getmarketNamebyproductid", map);
    }
    @Override
    public int getReviewCheck(HashMap<String, Object> map) {
        return sqlSession.selectOne("Review.getReviewCheck2", map);
    }

}
