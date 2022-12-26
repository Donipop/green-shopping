package com.green.shopping.dao;

import com.green.shopping.vo.QnAVo;
import com.green.shopping.vo.ReviewVo;

import java.util.HashMap;
import java.util.List;

public interface ReviewDao  {

    void reviewWrite(ReviewVo reviewVo);

    void QnAWrite(HashMap<String, Object> map);

    List<QnAVo> QnAList();

    QnAVo QnAReply(int id);

    void QnAreplyWrite(HashMap<String, Object> map);

}
