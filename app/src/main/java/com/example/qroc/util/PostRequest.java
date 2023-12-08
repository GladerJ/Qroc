package com.example.qroc.util;

import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PostRequest {
    public static String post(String url,String json){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS) // 设置连接超时时间为20秒
                .readTimeout(120, TimeUnit.SECONDS) // 设置读取超时时间为20秒
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        String responseData = null;
        try {
            Response response = client.newCall(request).execute();
            responseData = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }
}
