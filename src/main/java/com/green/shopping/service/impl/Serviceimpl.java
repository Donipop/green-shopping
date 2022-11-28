package com.green.shopping.service.impl;

import com.green.shopping.dao.Dao;
import com.green.shopping.service.Service;
import com.green.shopping.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@org.springframework.stereotype.Service
public class Serviceimpl implements Service {

    @Autowired
    private Dao dao;
    @Override
    public List<UserVo> getList() {

        List<UserVo> a = dao.getList();
        return a;
    }
}
