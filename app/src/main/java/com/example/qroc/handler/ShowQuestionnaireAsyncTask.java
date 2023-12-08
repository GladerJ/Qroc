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
import com.example.qroc.ShowQuestionnaire;
import com.example.qroc.pojo.User;
import com.example.qroc.pojo.behind.Questionnaire;
import com.example.qroc.util.CircleLoading;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.PostRequest;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ShowQuestionnaireAsyncTask extends AsyncTask<Void, Integer, Boolean> {
    private ShowQuestionnaire activity;
    private User user;
    private String json;
    private Dialog dialog;

    public ShowQuestionnaireAsyncTask(ShowQuestionnaire activity, User user) {
        this.activity = activity;
        this.user = user;
    }

    @Override
    protected void onPreExecute() {
        dialog = CircleLoading.showLoadDialog(activity, "搜索中...", true);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        CircleLoading.closeDialog(dialog);
        try {
            List<Questionnaire> questionnaires = JsonUtils.jsonToArrayQuestionnaire(json);
            activity.load(questionnaires);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            json = PostRequest.post(RegisterUser.URL + "/getQuestionnaires", JsonUtils.objectToJson(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json == null) return false;
        return true;
    }


}
