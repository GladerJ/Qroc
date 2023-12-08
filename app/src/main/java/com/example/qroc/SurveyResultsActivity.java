package com.example.qroc;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.qroc.R;
import com.example.qroc.handler.SurveyResultActivityAsyncTask;
import com.example.qroc.pojo.behind.Option;
import com.example.qroc.pojo.behind.Problem;
import com.example.qroc.pojo.behind.Questionnaire;

import java.util.ArrayList;
import java.util.List;

public class SurveyResultsActivity extends AppCompatActivity {
    private Questionnaire questionnaire;
    private TextView createTime;
    private TextView updateTime;
    private TextView people;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_results);
        createTime = findViewById(R.id.create_time_2);
        updateTime = findViewById(R.id.update_time_2);
        people = findViewById(R.id.people);
        Intent intent = getIntent();
        long id = Long.parseLong(intent.getStringExtra("ID"));
        SurveyResultActivityAsyncTask surveyResultActivityAsyncTask = new SurveyResultActivityAsyncTask(SurveyResultsActivity.this,id);
        surveyResultActivityAsyncTask.execute();

    }
    public void show(){
        // 设置问卷标题
        createTime.setText("创建时间:" + questionnaire.getCreateTime());
        updateTime.setText("更新时间:" + questionnaire.getUpdateTime());
        people.setText("已有" + questionnaire.getPeople() + "人参与了该问卷调查");

        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(questionnaire.getTitle());

        // 获取问题布局
        LinearLayout questionLayout = findViewById(R.id.questionLayout);

        // 创建问题视图
        LayoutInflater inflater = LayoutInflater.from(this);
        ArrayList<Problem> problems = questionnaire.getProblems();
        for (Problem problem : problems) {
            // 加载问题布局
            View questionView = inflater.inflate(R.layout.layout_question, questionLayout, false);

            // 设置问题题目
            TextView questionTitleTextView = questionView.findViewById(R.id.questionTitleTextView);
            questionTitleTextView.setText(problem.getNum() + "." + problem.getContent());

            // 获取选项布局
            LinearLayout optionLayout = questionView.findViewById(R.id.optionLayout);

            // 创建选项视图
            int sum = 0;
            for(Option option : problem.getOptions()){
                sum += option.getCount();
            }
            for (Option option : problem.getOptions()) {
                // 加载选项布局
                View optionView = inflater.inflate(R.layout.option_layout, optionLayout, false);

                // 设置选项文本
                TextView optionTextView = optionView.findViewById(R.id.optionTextView);
                optionTextView.setText(option.getOptionNum() + option.getContent() + "(" + option.getCount()+")");

                // 设置进度条
                ProgressBar progressBar = optionView.findViewById(R.id.progressBar);
                progressBar.setMax(sum);
                int now = 0;
                now += option.getCount();
                progressBar.setProgress(now);

                // 将选项视图添加到选项布局
                optionLayout.addView(optionView);
            }

            // 将问题视图添加到问题布局
            questionLayout.addView(questionView);
        }
    }

    // 假设有一个方法来获取问卷数据
    public void setQuestionnaire(Questionnaire questionnaire) {
        // 获取问卷数据的逻辑
        // 返回问卷对象
        this.questionnaire = questionnaire;
    }
}