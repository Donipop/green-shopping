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

    @PostMapping("/view/review")
    public void ReviewBoard(@RequestBody HashMap<String, Object> map) {
        ObjectMapper objectMapper = new ObjectMapper();
        ReviewVo reviewVo = objectMapper.convertValue(map, ReviewVo.class);
        System.out.println(map);

        reviewService.reviewWrite(reviewVo);

    }
        @PostMapping("/view/qna/1234")
         public void qna(@RequestBody HashMap<String, Object> map){

        reviewService.QnAWrite(map);
            System.out.println(map);
        }

        @GetMapping("/view/QnA")
        public List<QnAVo> QnAList(){


               return reviewService.QnAList();
        }

       @GetMapping("/QnA/reply/{id}")
       public QnAVo QnAreply (@PathVariable("id") int id ){

        return reviewService.QnAreply(id);

       }

       @PostMapping("/view/Qna/write")
      public void QnAreplyWrite(@RequestBody HashMap<String, Object> map){
           reviewService.QnAreplyWrite(map);
       }



    }

