package com.example.qroc.handler;

import com.example.qroc.RegisterUser;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.MMKVUtils;
import com.example.qroc.util.PostRequest;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

public class TokenThread extends Thread{
    private Result tokenResult;
    @Override
    public void run() {
        Result result = new Result();
        result.setData(MMKVUtils.getString("token"));
        String json = null;
        try {
            json = JsonUtils.objectToJson(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(json != null){
            String respond = PostRequest.post(RegisterUser.URL + "/verifyToken",json);
            try {
                tokenResult = JsonUtils.jsonToResult(respond);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
    public Result getResult(){
        return tokenResult;
    }
}
