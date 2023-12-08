package com.example.qroc.handler;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import com.example.qroc.LoginUser;
import com.example.qroc.MainActivity;
import com.example.qroc.RegisterUser;
import com.example.qroc.pojo.User;
import com.example.qroc.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;

public class LoginAsyncTask extends AsyncTask<Void, Integer, Boolean> {
    private String userName, passWord;
    private Result result1;
    private Activity activity;
    private Dialog dialog;
    private String dir;

    public LoginAsyncTask(String userName, String passWord, Activity activity, String dir) {
        this.userName = userName;
        this.passWord = passWord;
        this.activity = activity;
        this.dir = dir;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //显示

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        CircleLoading.closeDialog(dialog);
        if (result1 == null) {
            Toast.makeText(activity, "网络连接失败！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (result1.getCode() == 0) {
            Toast.makeText(activity, result1.getMsg(), Toast.LENGTH_SHORT).show();
        }

        //跳转到主页面
        else {
            Toast.makeText(activity, "登陆成功！", Toast.LENGTH_SHORT).show();

            MMKVUtils.init(dir);
            MMKVUtils.putString("token", (String) result1.getData());


            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();

        }
    }


    @Override
    protected void onPreExecute() {
        dialog = CircleLoading.showLoadDialog(activity, "加载中...", true);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        User user = new User();
        //:lpl
        //:123456
        //{"username":"lpl","password":"123456"}
        user.setUsername(userName);
        user.setPassword(passWord);
        String json = null;
        try {
            json = JsonUtils.objectToJson(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String respond = PostRequest.post(RegisterUser.URL + "/login", json);
        if (respond == null) return false;
        try {
            result1 = JsonUtils.jsonToResult(respond);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (result1.getCode() == 1) return true;
        return false;
    }

    public Result getResult() {
        return result1;
    }
}
