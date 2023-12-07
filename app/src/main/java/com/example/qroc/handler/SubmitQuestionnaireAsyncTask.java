package com.example.qroc.handler;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.qroc.LoginUser;
import com.example.qroc.R;
import com.example.qroc.RegisterUser;
import com.example.qroc.util.CircleLoading;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.PostRequest;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

public class SubmitQuestionnaireAsyncTask extends AsyncTask<Void,Integer,Boolean> {
    private Activity activity;
    private ArrayList<Long> arrayList;
    private Result result;
    private Dialog dialog;

    public SubmitQuestionnaireAsyncTask(Activity activity, ArrayList<Long> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @Override
    protected void onPreExecute() {
        dialog = CircleLoading.showLoadDialog(activity, "加载中...", true);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        CircleLoading.closeDialog(dialog);
        if (!aBoolean){
            Toast.makeText(activity,"未知错误，请联系管理员!",Toast.LENGTH_SHORT).show();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("提交成功：").setMessage("感谢您参与本次问卷调查！");
            builder.setIcon(R.mipmap.ic_launcher_round);
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String json = JsonUtils.objectToJson(arrayList);
            String respond = PostRequest.post(RegisterUser.URL + "/submitQuestionnaire",json);
            result = JsonUtils.jsonToResult(respond);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result.getCode() == 0) return false;
        return true;
    }
}
