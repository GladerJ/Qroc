package com.example.qroc.util;

import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.*;

import java.io.IOException;

public class PostRequest {
    public static String post(String url,String json){
        OkHttpClient client = new OkHttpClient();
        Request request = null;//创建Http请求
        request = new Request.Builder()
                .url(url)//***.***.**.***为本机IP，xxxx为端口，/  /  为访问的接口后缀
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();
        String responseData = null;
        try {
            Response response = client.newCall(request).execute();//执行发送的指令
            responseData = response.body().string();//获取返回的结果
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }
}
