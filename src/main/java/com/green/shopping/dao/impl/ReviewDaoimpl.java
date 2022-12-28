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
    public void reviewWrite(ReviewVo reviewVo) {
        sqlSession.insert("Review.ReviewWrite", reviewVo);
    }

    @Override
    public void QnAWrite(HashMap<String, Object> map) {
        sqlSession.insert("Review.QnAWrite", map);
    }

    @Override
    public List<QnAVo> QnAList(@PathVariable("page") int page) {
        return sqlSession.selectList("Review.QnAList", page);
    }

    @Override
    public QnAVo QnAReply(@RequestParam HashMap<String ,Object> map) {
        return sqlSession.selectOne("Review.QnAReply", map);
    }

    @Override
    public void QnAreplyWrite(HashMap<String, Object> map) {
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


}
