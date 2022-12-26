package com.green.shopping.service;

import com.green.shopping.dao.ReviewDao;
import com.green.shopping.vo.QnAVo;
import com.green.shopping.vo.ReviewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewDao reviewDao;
    public void reviewWrite(ReviewVo reviewVo) {
        reviewDao.reviewWrite(reviewVo);
    }

    public void QnAWrite(HashMap<String, Object> map) {
        reviewDao.QnAWrite(map);
    }

    public List<QnAVo> QnAList() {

        return reviewDao.QnAList();

    }

    public QnAVo QnAreply(int id) {
        return reviewDao.QnAReply(id);
    }

    public void QnAreplyWrite(HashMap<String, Object> map) {
        reviewDao.QnAreplyWrite(map);
    }


}
