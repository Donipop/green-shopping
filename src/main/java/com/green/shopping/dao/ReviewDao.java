package com.green.shopping.dao;

import com.green.shopping.vo.QnAVo;
import com.green.shopping.vo.ReviewVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

public interface ReviewDao  {

    void reviewWrite(ReviewVo reviewVo);

    void QnAWrite(HashMap<String, Object> map);

    List<QnAVo> QnAList(@PathVariable("page") int page);

    QnAVo QnAReply(@RequestParam HashMap<String ,Object> map);

    void QnAreplyWrite(HashMap<String, Object> map);

    List<ReviewVo> reviewList(int page);

    void reviewDelete(HashMap<String, Object> map);

    ReviewVo reviewUpdateForm(HashMap<String, Object> map);

    List<QnAVo> QnareplyList(HashMap<String, Object> map);
    void reviewUpdate(HashMap<String, Object> map);
}
