package com.green.shopping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.shopping.service.ReviewService;
import com.green.shopping.vo.QnAVo;
import com.green.shopping.vo.ReviewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/view/reviewWrite/{page}")
    public void ReviewBoard(@RequestBody HashMap<String, Object> map) {
        ObjectMapper objectMapper = new ObjectMapper();
        ReviewVo reviewVo = objectMapper.convertValue(map, ReviewVo.class);
        System.out.println(map);

        reviewService.reviewWrite(reviewVo);

    }
        @PostMapping("/view/QnA/write/{page}")
         public void qna(@RequestBody HashMap<String, Object> map){

        reviewService.QnAWrite(map);
            System.out.println(map);
        }

        @GetMapping("/view/QnA/{page}")
        public List<QnAVo> QnAList(@PathVariable("page") int page ){

               return reviewService.QnAList(page);
        }

       @GetMapping("/QnA/reply/{page}/{id}")
       public QnAVo QnAreply (@RequestParam HashMap<String ,Object> map){
           System.out.println(map);
         return reviewService.QnAreply(map);

       }

    @GetMapping("/QnA/replyList/{page}")
    public List<QnAVo> QnAreplyList (@RequestParam HashMap<String ,Object> map){

        return reviewService.QnareplyList(map);

    }

       @PostMapping("/view/Qna/write")
      public void QnAreplyWrite(@RequestBody HashMap<String, Object> map){
           reviewService.QnAreplyWrite(map);
       }

    @GetMapping("/view/review/{page}")
    public List<ReviewVo> reviewList(@PathVariable("page") int page) {
        return reviewService.reviewList(page);

    }

    @PostMapping("/view/review/delete/{id}/{page}")
    public void reviewDelete(@RequestBody HashMap<String, Object> map){
        System.out.println(map);
        reviewService.reviewDelete(map);
    }

    @GetMapping("/view/reviewUpdateForm/{page}/{id}")
    public ReviewVo reviewUpdateForm(@RequestParam HashMap<String, Object> map){

        return reviewService.reviewUpdateForm(map);

    }


    }

