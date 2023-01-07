package com.green.shopping.dao;

import java.util.HashMap;

public interface FileDao {
    String uploadFile(String name,String File_Type, String userId);
    String deleteFile(String Id);
    String updateFile(String Id, String name, String fileType, String fileSize, String width, String height, String userId);
    HashMap<String,Object> getFile(String Id);
}
