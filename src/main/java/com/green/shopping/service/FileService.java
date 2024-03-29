package com.green.shopping.service;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.green.shopping.dao.impl.FileDaoImpl;
import org.apache.ibatis.session.SqlSession;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileService {
    private final FileDaoImpl fileDaoimpl;
    public FileService(FileDaoImpl fileDaoimpl) {
        this.fileDaoimpl = fileDaoimpl;
    }
    public String fileUpload(String Data,String user_Id) {
        String dbInsert = fileDBInsert(Data);
        if(dbInsert != "error"){
            String[] filename = dbInsert.split("\\^");
            fileDaoimpl.uploadFile(filename[0], filename[1], user_Id);
            return filename[0];
        }else {
            return "error";
        }
    }
    public String fileDelete(String fileName) {
        //이미지 서버에서 삭제
        try{
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("filename", fileName);
            URL url = new URL("http://donipop.com:3333/single/delete");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            OutputStream os = conn.getOutputStream();
            os.write(jsonObject.toString().getBytes("UTF-8"));
            os.flush();
            os.close();
            conn.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            conn.disconnect();
            if(response.toString().contains("ok")){
                fileDeleteToDB(fileName.split("\\.")[0]);
                return "success";
            }else{
                return "fail";
            }
        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
        //DB에서 삭제
    }

    private void fileDeleteToDB(String fileName) {
        fileDaoimpl.deleteFile(fileName);
        //product_img_tb에서삭제
        //
    }

    public int fileUpdate() {
        return 0;
    }

    private String fileDBInsert(String Data) {
        //data -> json 변환
        JsonObject jsonObject = new JsonObject();
        HashMap<String,String> map = new HashMap<>();
        String fileData = Data.split("base64,")[1];
        String extension = (Data.split("data:")[1].split(";base64,")[0]).split("/")[1];
        map.put("data",fileData);
        map.put("extension", extension);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            jsonObject.addProperty(entry.getKey(), entry.getValue());
        }
        //이미지 서버에 업로드
        try{
            URL url = new URL("http://donipop.com:3333/single/upload");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            OutputStream os = conn.getOutputStream();
            os.write(jsonObject.toString().getBytes("UTF-8"));
            os.flush();
            os.close();
            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            conn.disconnect();
            return response.toString();

        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    public HashMap<String,Object> getFile(String fileName) {
        return fileDaoimpl.getFile(fileName);
    }
}
