package com.example.qroc.handler;

import android.app.Dialog;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.qroc.CreateQuestionnaire;
import com.example.qroc.RegisterUser;
import com.example.qroc.SurveyResultsActivity;
import com.example.qroc.pojo.behind.Problem;
import com.example.qroc.pojo.behind.Questionnaire;
import com.example.qroc.util.CircleLoading;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.PostRequest;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

public class UpdateQuestionnaireAsyncTask extends AsyncTask<Void,Integer,Boolean> {
    private CreateQuestionnaire activity;
    private Long id;
    private String json;
    private Dialog dialog;
    private LinearLayout mainLayout;
    private Button save;

    public UpdateQuestionnaireAsyncTask(CreateQuestionnaire activity, Long id, LinearLayout mainLayout,Button save) {
        this.activity = activity;
        this.id = id;
        this.mainLayout = mainLayout;
        this.save = save;
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
                Questionnaire questionnaire = JsonUtils.jsonToQuestionnaire(json);
                activity.setTitle(questionnaire.getTitle());
                ArrayList<Problem> problems = questionnaire.getProblems();
                problems.stream().forEach(problem->{
                    activity.addProblem(problem);
                });
                mainLayout.removeView(save);
                mainLayout.addView(save);
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
