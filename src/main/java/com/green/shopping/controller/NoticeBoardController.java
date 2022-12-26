package com.green.shopping.controller;

import com.green.shopping.service.BoardService;
import com.green.shopping.vo.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoticeBoardController {

    @Autowired
    BoardService boardService;


    @GetMapping("/Notice")
    public List<NoticeVo> BoardList(){
        return boardService.BoardList();

    }

    @GetMapping("/NoticeDetail/{id}")
    public NoticeVo BoardDetail(@PathVariable("id") int id){

        return boardService.boardDetail(id);
    }

}
