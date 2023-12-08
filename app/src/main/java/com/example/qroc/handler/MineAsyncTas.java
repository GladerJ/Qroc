package com.example.qroc.handler;

import android.app.Dialog;
import android.os.AsyncTask;
import android.widget.Toast;
import com.example.qroc.MainActivity;
import com.example.qroc.RegisterUser;
import com.example.qroc.pojo.User;
import com.example.qroc.util.CircleLoading;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.PostRequest;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;


public class MineAsyncTas extends AsyncTask<Void, Integer, Boolean> {
    public MineAsyncTas(MainActivity activity, String token) {
        this.activity = activity;
        this.token = token;
    }

    private String ans;
    private Dialog dialog;
    private MainActivity activity;
    private String token;

    public MineAsyncTas() {

    }

    @Override
    protected void onPreExecute() {
        dialog = CircleLoading.showLoadDialog(activity, "加载中...", true);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        CircleLoading.closeDialog(dialog);
        if (aBoolean) {
            try {
                User user = JsonUtils.jsonToUser(ans);
                activity.setUsername1(user.getUsername());
                activity.setEmail(user.getEmail());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Result result = Result.success(token);
        try {
            String respond =
                    PostRequest.post(RegisterUser.URL + "/verifyToken", JsonUtils.objectToJson(result));
            ans = respond;
        } catch (JsonProcessingException e) {
            return false;
        }

        if (ans == null) return false;
        return true;

    }

}
