package com.green.shopping.dao;

import com.green.shopping.vo.NoticeVo;

import java.util.List;

public interface BoardDao {
    List<NoticeVo> BoardList();

    NoticeVo boardDetail(int id);
}
