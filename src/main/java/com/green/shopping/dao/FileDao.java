package com.green.shopping.dao;

public interface FileDao {
    String uploadFile(String name,String File_Type, String userId);
    String deleteFile(String Id);
    String updateFile(String Id, String name, String fileType, String fileSize, String width, String height, String userId);
}
