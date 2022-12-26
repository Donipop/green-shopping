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
         return reviewService.QnAreply(map);

       }

       @PostMapping("/view/Qna/write")
      public void QnAreplyWrite(@RequestBody HashMap<String, Object> map){
           reviewService.QnAreplyWrite(map);
       }



    }

