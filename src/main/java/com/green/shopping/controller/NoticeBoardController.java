package com.green.shopping.controller;

import com.green.shopping.service.BoardService;
import com.green.shopping.vo.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoticeBoardController {

    @Autowired
    BoardService boardService;


    @GetMapping("/Notice")
    public List<NoticeVo> BoardList(){
        System.out.println(boardService.BoardList());
        return boardService.BoardList();

    }

}
