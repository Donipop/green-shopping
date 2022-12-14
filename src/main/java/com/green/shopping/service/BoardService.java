package com.green.shopping.service;

import com.green.shopping.dao.BoardDao;
import com.green.shopping.vo.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardDao boardDao;
    public List<NoticeVo> BoardList() {
        return boardDao.BoardList();
    }
}
