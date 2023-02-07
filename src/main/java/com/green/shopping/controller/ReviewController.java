package com.green.shopping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.shopping.service.ReviewService;
import com.green.shopping.vo.QnAVo;
import com.green.shopping.vo.ReviewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/view/reviewWrite/{page}")
    public void ReviewWrite(@RequestBody HashMap<String, Object> map) {
        ObjectMapper objectMapper = new ObjectMapper();
        ReviewVo reviewVo = objectMapper.convertValue(map, ReviewVo.class);

        reviewService.ReviewWrite(reviewVo);

    }

    @GetMapping("/view/review/{page}")
    public List<ReviewVo> reviewList(@PathVariable("page") int page) {
        return reviewService.reviewList(page);

    }

    @PostMapping("/view/review/delete/{id}/{page}")
    public void reviewDelete(@RequestBody HashMap<String, Object> map) {
        reviewService.reviewDelete(map);
    }

    @GetMapping("/view/reviewUpdateForm/{page}/{id}")
    public ReviewVo reviewUpdateForm(@RequestParam HashMap<String, Object> map) {

        return reviewService.reviewUpdateForm(map);

    }

    @PostMapping("/view/reviewUpdate/{page}/{id}")
    public void reviewUpdate(@RequestBody HashMap<String, Object> map) {
        reviewService.reviewUpdate(map);
    }


    //질문 작성
    @PostMapping("/view/QnA/write/{page}")
    public void QuestionWrite(@RequestBody HashMap<String, Object> map) {

        reviewService.QuestionWrite(map);
    }

    //질문 팝업창 띄울 시 product_name가져오는 메서드
    @GetMapping("/view/QnA/write/getproductname/{page}")
    public String getProductName(@PathVariable("page") int page) {
        return reviewService.getProductName(page);
    }


    //질문 리스트
    @GetMapping("/view/QnA/{page}")
    public List<QnAVo> QuestionList(@PathVariable("page") int page) {

        return reviewService.QuestionList(page);
    }


    //답변하기 클릭 시 질문내용 들고오기
    @GetMapping("/QnA/reply/{page}/{id}")
    public QnAVo QuestionOneList(@RequestParam HashMap<String, Object> map) {
        return reviewService.QuestionOneList(map);

    }

    //질문 수정 시 질문내용 들고오는 곳
    @GetMapping("/QnA/QuestionList/{page}/{id}")
    public QnAVo QuestionUpdateForm(@RequestParam HashMap<String, Object> map) {
        return reviewService.QuestionUpdateForm(map);

    }

    @PostMapping("/view/QuestionUpdate/{page}/{id}")
    public void QuestionUpdate(@RequestBody HashMap<String, Object> map) {
        reviewService.QuestionUpdate(map);
    }

    //@GetMapping("/QnA/replyList/{page}")
    // public List<QnAVo> QnAreplyList (@RequestParam HashMap<String ,Object> map){

    //    return reviewService.QnareplyList(map);

    // }map


    //답변하기 작성
    @PostMapping("/view/AnswerWrite/")
    public void AnswerWrite(@RequestBody HashMap<String, Object> map) {
        reviewService.AnswerWrite(map);
    }

    //질문


    @PostMapping("/view/QnA/QuestionDelete/{page}/{id}")
    public void QuestionDelete(@RequestBody HashMap<String, Object> map) {
        reviewService.QuestionDelete(map);

    }

    @PostMapping("/view/QnA/QuestionHardDelete/{page}/{id}")
    public void QuestionHardDelete(@RequestBody HashMap<String, Object> map) {
        reviewService.QuestionHardDelete(map);

    }

    @PostMapping("/view/QnA/answerDelete/{page}/{id}")
    public void answerDelete(@RequestBody HashMap<String, Object> map) {
        reviewService.answerDelete(map);

    }

    @PostMapping("view/QnA/answerUpdate/{page}/{id}")
    public void answerUpdate(@RequestParam HashMap<String, Object> map) {
        reviewService.answerUpdate(map);
    }

    @GetMapping("/view/marketcheck")
    public boolean marketcheck(@RequestParam HashMap<String, Object> map) {
        try {
            String a = reviewService.getmarketName(map);
            String b = reviewService.getmarketNamebyproductid(map);
            if (a.equals(b)) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }
    @GetMapping("/view/reviewCheck/{page}")
    public boolean getReviewCheck(@RequestParam HashMap<String, Object> map) {
        try {
            List<HashMap<String, Object>>  reviewCheck = reviewService.getReviewCheck(map);
            if(reviewCheck.size() == 0){
                return false;
            } else {
                return true;
            }
        }
        catch (Exception e) {
            return false;
        }
    }
}

