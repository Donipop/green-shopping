package com.green.shopping.dao.impl;

import com.green.shopping.dao.FileDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class FileDaoImpl implements FileDao {
    @Autowired
    private final SqlSession sqlSession;

    public FileDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public String uploadFile(String name,String File_Type, String userId) {
        HashMap<String, String> uploadFileMap = new HashMap<>();
        uploadFileMap.put("name",name);
        uploadFileMap.put("File_Type",File_Type);
        uploadFileMap.put("userId",userId);
        sqlSession.insert("File.upload",uploadFileMap);
        return name;
    }

    @Override
    public String deleteFile(String Id) {
        return "";
    }

    @Override
    public String updateFile(String Id, String name, String fileType, String fileSize, String width, String height, String userId) {
        return "";
    }
}
