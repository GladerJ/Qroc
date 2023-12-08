package com.example.qroc.handler;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import androidx.appcompat.app.AlertDialog;
import com.example.qroc.*;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.MMKVUtils;
import com.example.qroc.util.PostRequest;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

public class MyAsyncTask extends AsyncTask<Void,Integer,Boolean> {
    private ProgressBar progressBar;
    private String dir;
    private Loading loading;
    private Result result;
    public MyAsyncTask(ProgressBar progressBar,String dir,Loading loading){
        this.progressBar = progressBar;
        this.dir = dir;
        this.loading = loading;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(!aBoolean)
            loading.longTimeError();
    }

    @Override
    protected void onPreExecute() {
        progressBar.setProgress(0);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
                //子线程开始

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MMKVUtils.init(dir);
                Result r = new Result();
                r.setData(MMKVUtils.getString("token"));
                String json = null;
                try {
                    json = JsonUtils.objectToJson(r);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                String respond = PostRequest.post(RegisterUser.URL + "/verifyToken", json);
                if(respond != null){
                    try {
                        result = JsonUtils.jsonToResult(respond);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        thread.start();

        for(int i=1;i<=1000;i++){
            publishProgress(i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i >= 50 && result != null){
                publishProgress(1000);
                loading.decide(result);
                return true;
            }
        }
        return false;
    }
}
