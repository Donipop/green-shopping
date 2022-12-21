package com.green.shopping.service;

import com.green.shopping.dao.impl.FileDaoImpl;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class FileService {
    private final FileDaoImpl fileDaoimpl;

    public FileService(FileDaoImpl fileDaoimpl) {
        this.fileDaoimpl = fileDaoimpl;
    }
    public String fileUpload(String Data) {
        //이미지 서버에 업로드 -> 업로드한 uuid를 Id로 가지는 파일 정보를 DB에 저장
        try{
            URL url = new URL("http://donipop.com:3333/single/upload");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(Data.length()));
            connection.connect();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            writer.write(Data);
            writer.flush();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String result = reader.readLine();
            writer.close();
            reader.close();
            connection.disconnect();
            String[] resultArray = result.split(".");
            String userId = "";
            fileDaoimpl.uploadFile(resultArray[0],resultArray[1],userId);
            return resultArray[0];
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    public int fileDelete() {
        return 0;
    }
    public int fileUpdate() {
        return 0;
    }
}
