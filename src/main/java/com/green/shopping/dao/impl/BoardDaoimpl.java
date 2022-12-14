package com.green.shopping.dao.impl;

import com.green.shopping.dao.BoardDao;
import com.green.shopping.vo.NoticeVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDaoimpl implements BoardDao {

    @Autowired
    private SqlSession sqlSession;
    @Override
    public List<NoticeVo> BoardList() {
        List<NoticeVo> a = sqlSession.selectList("Board.BoardList");
        return a;
    }
}
