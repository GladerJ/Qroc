package com.example.qroc.handler;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import com.example.qroc.QuestionnaireActivity;
import com.example.qroc.R;
import com.example.qroc.RegisterUser;
import com.example.qroc.pojo.behind.Questionnaire;
import com.example.qroc.util.CircleLoading;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.PostRequest;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

public class SearchQuestionnaireAsyncTask extends AsyncTask<Void,Integer,Boolean> {
    private Activity activity;
    private Long id;
    private String json;
    private Dialog dialog;

    public SearchQuestionnaireAsyncTask(Activity activity,Long id) {
        this.activity = activity;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        dialog = CircleLoading.showLoadDialog(activity, "搜索中...", true);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        CircleLoading.closeDialog(dialog);
        if(!aBoolean){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("未搜索到问卷：").setMessage("未查询到此问卷信息，请检查问卷ID是否输入错误！");
            builder.setIcon(R.mipmap.ic_launcher_round);
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setCancelable(false);
            builder.show();
            return;
        }
        Intent intent = new Intent(activity, QuestionnaireActivity.class);
        intent.putExtra("questionnaire", json);
        activity.startActivity(intent);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setQuestionnaireId(id);
        try {
            json = PostRequest.post(RegisterUser.URL + "/searchQuestionnaire", JsonUtils.objectToJson(questionnaire));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Result result = JsonUtils.jsonToResult(json);
            if(result.getCode() == 0) return false;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return true;
    }
}
