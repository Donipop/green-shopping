package com.green.shopping.dao.impl;

import com.green.shopping.dao.ReviewDao;
import com.green.shopping.vo.QnAVo;
import com.green.shopping.vo.ReviewVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public List<QnAVo> QnAList() {
        return sqlSession.selectList("Review.QnAList");
    }

    @Override
    public QnAVo QnAReply(int id) {
        return sqlSession.selectOne("Review.QnAReply", id);
    }

    @Override
    public void QnAreplyWrite(HashMap<String, Object> map) {
        sqlSession.insert("Review.QnAreplyWrite", map);
    }


}
