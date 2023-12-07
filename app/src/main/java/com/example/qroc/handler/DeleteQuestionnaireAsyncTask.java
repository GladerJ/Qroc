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

public class DeleteQuestionnaireAsyncTask extends AsyncTask<Void,Integer,Boolean> {
    private Activity activity;
    private Result result;
    private Dialog dialog;
    Questionnaire questionnaire;

    public DeleteQuestionnaireAsyncTask(Activity activity,Questionnaire questionnaire) {
        this.activity = activity;
        this.questionnaire = questionnaire;
    }

    @Override
    protected void onPreExecute() {
        dialog = CircleLoading.showLoadDialog(activity, "删除中...", true);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        CircleLoading.closeDialog(dialog);
        System.out.println(aBoolean);
        if(aBoolean){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("删除成功：").setMessage("问卷删除成功！");

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
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String json = PostRequest.post(RegisterUser.URL + "/deleteQuestionnaire",JsonUtils.objectToJson(questionnaire));
            result = JsonUtils.jsonToResult(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(result.getCode() == 0) return false;
        return true;
    }
}
