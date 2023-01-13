package com.green.shopping.dao;

import com.green.shopping.vo.QnAVo;
import com.green.shopping.vo.ReviewVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

public interface ReviewDao  {

    void ReviewWrite(ReviewVo reviewVo);

    List<ReviewVo> reviewList(int page);

    void reviewDelete(HashMap<String, Object> map);

    ReviewVo reviewUpdateForm(HashMap<String, Object> map);

    void reviewUpdate(HashMap<String, Object> map);


    void QuestionWrite(HashMap<String, Object> map);

    List<QnAVo> QuestionList(@PathVariable("page") int page);

    QnAVo QuestionOneList(@RequestParam HashMap<String ,Object> map);

    void AnswerWrite(HashMap<String, Object> map);

    List<QnAVo> QnareplyList(HashMap<String, Object> map);

    void QuestionDelete(HashMap<String, Object> map);

    void answerDelete(HashMap<String, Object> map);

    QnAVo QuestionUpdateForm(HashMap<String, Object> map);

    void QuestionUpdate(HashMap<String, Object> map);

    void answerUpdate(HashMap<String, Object> map);

    void QuestionHardDelete(HashMap<String, Object> map);

    String getProductName(int page);

}
