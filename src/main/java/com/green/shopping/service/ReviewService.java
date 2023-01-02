package com.green.shopping.service;

import com.green.shopping.dao.ReviewDao;
import com.green.shopping.vo.QnAVo;
import com.green.shopping.vo.ReviewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewDao reviewDao;
    public void ReviewWrite(ReviewVo reviewVo) {
        reviewDao.ReviewWrite(reviewVo);
    }

    public List<ReviewVo> reviewList(int page) {
        return reviewDao.reviewList(page);
    }

    public void reviewDelete(HashMap<String, Object> map) {
        reviewDao.reviewDelete(map);
    }

    public ReviewVo reviewUpdateForm(HashMap<String, Object> map) {
        return reviewDao.reviewUpdateForm(map);
    }

    public void reviewUpdate(HashMap<String, Object> map) {
        reviewDao.reviewUpdate(map);
    }

    public void QuestionWrite(HashMap<String, Object> map) {
        reviewDao.QuestionWrite(map);
    }

    public List<QnAVo> QuestionList(@PathVariable("page") int page) {

        return reviewDao.QuestionList(page);

    }

    public QnAVo QuestionOneList(@RequestParam HashMap<String ,Object> map) {
        return reviewDao.QuestionOneList(map);
    }

    public void AnswerWrite(HashMap<String, Object> map) {
        reviewDao.AnswerWrite(map);
    }




    public List<QnAVo> QnareplyList(HashMap<String, Object> map) {
        return reviewDao.QnareplyList(map);
    }



    public void QuestionDelete(HashMap<String, Object> map) {
         reviewDao.QuestionDelete(map);
    }

    public void answerDelete(HashMap<String, Object> map) {
        reviewDao.answerDelete(map);
    }

    public QnAVo QuestionUpdateForm(HashMap<String, Object> map) {
        return reviewDao.QuestionUpdateForm(map);
    }

    public void QuestionUpdate(HashMap<String, Object> map) {
         reviewDao.QuestionUpdate(map);
    }

    public void answerUpdate(HashMap<String, Object> map) {
        reviewDao.answerUpdate(map);
    }
}
