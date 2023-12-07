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
import com.example.qroc.SurveyResultsActivity;
import com.example.qroc.pojo.behind.Questionnaire;
import com.example.qroc.util.CircleLoading;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.PostRequest;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

public class SurveyResultActivityAsyncTask extends AsyncTask<Void,Integer,Boolean> {
    private SurveyResultsActivity activity;
    private Long id;
    private String json;
    private Dialog dialog;

    public SurveyResultActivityAsyncTask(SurveyResultsActivity activity,Long id) {
        this.activity = activity;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        dialog = CircleLoading.showLoadDialog(activity, "加载中...", true);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        CircleLoading.closeDialog(dialog);
        if(aBoolean){
            try {
                activity.setQuestionnaire(JsonUtils.jsonToQuestionnaire(json));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            activity.show();
        }
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
            json = PostRequest.post(RegisterUser.URL + "/searchQuestionnaire",JsonUtils.objectToJson(questionnaire));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(json == null){
            return false;
        }
        return true;
    }
}
