package com.example.qroc.handler;

import android.app.Dialog;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.qroc.CreateQuestionnaire;
import com.example.qroc.RegisterUser;
import com.example.qroc.pojo.behind.Problem;
import com.example.qroc.pojo.behind.Questionnaire;
import com.example.qroc.util.CircleLoading;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.PostRequest;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

public class AiCreateQuestionnaireAsyncTask extends AsyncTask<Void,Integer,Boolean> {
    private CreateQuestionnaire activity;
    private String data;
    private Questionnaire questionnaire;
    private Dialog dialog;
    private LinearLayout mainLayout;
    private Button save;

    public AiCreateQuestionnaireAsyncTask(CreateQuestionnaire activity, String data, LinearLayout mainLayout,Button save) {
        this.activity = activity;
        this.data = data;
        this.mainLayout = mainLayout;
        this.save = save;
    }

    @Override
    protected void onPreExecute() {
        dialog = CircleLoading.showLoadDialog(activity, "ai思考中...", true);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        CircleLoading.closeDialog(dialog);
        if(aBoolean){
            try {
                activity.setTitle(questionnaire.getTitle());
                ArrayList<Problem> problems = questionnaire.getProblems();
                problems.stream().forEach(problem->{
                    activity.addProblem(problem);
                });
                mainLayout.removeView(save);
                mainLayout.addView(save);
            } catch (Exception e) {
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
        Result result = Result.success(data);
        try {
            String json = PostRequest.post(RegisterUser.URL + "/chat",JsonUtils.objectToJson(result));
            questionnaire = JsonUtils.jsonToQuestionnaire(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(questionnaire == null){
            return false;
        }
        return true;
    }

}
