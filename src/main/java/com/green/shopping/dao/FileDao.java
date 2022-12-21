package com.green.shopping.dao;

public interface FileDao {
    int uploadFile(String name,String File_Type, String userId);
    int deleteFile(String Id);
    int updateFile(String Id, String name, String fileType, String fileSize, String width, String height, String userId);
}
