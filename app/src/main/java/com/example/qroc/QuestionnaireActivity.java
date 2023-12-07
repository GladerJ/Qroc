package com.example.qroc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.qroc.handler.SubmitQuestionnaireAsyncTask;
import com.example.qroc.pojo.behind.Option;
import com.example.qroc.pojo.behind.Problem;
import com.example.qroc.pojo.behind.Questionnaire;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

public class QuestionnaireActivity extends AppCompatActivity {
    private TextView titleTextView, createTimeTextView, updateTimeTextView, usernameTextView;
    private LinearLayout problemLayout;
    private Button submitButton;
    private Questionnaire questionnaire = null;
    private ArrayList<Long> postOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        Intent intent = getIntent();
        Result result;
        try {
            String json = intent.getStringExtra("questionnaire");
            questionnaire = JsonUtils.jsonToQuestionnaire(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 初始化视图组件
        titleTextView = findViewById(R.id.title_textview);
        createTimeTextView = findViewById(R.id.create_time_textview);
        updateTimeTextView = findViewById(R.id.update_time_textview);
        usernameTextView = findViewById(R.id.username_textview);
        problemLayout = findViewById(R.id.problem_layout);
        submitButton = findViewById(R.id.submit_button);

        // 设置问卷信息
        titleTextView.setText(questionnaire.getTitle());
        createTimeTextView.setText("创建时间：" + questionnaire.getCreateTime());
        updateTimeTextView.setText("更新时间：" + questionnaire.getUpdateTime());
        usernameTextView.setText("所属用户：" + questionnaire.getUsername());

        // 添加问题
        ArrayList<Problem> problems = questionnaire.getProblems();
        for(Problem problem : problems){
            addProblem(problem);
        }

        // 设置提交按钮的点击事件
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里处理提交答案的逻辑
                int size = questionnaire.getProblems().size();
                postOptions = new ArrayList<>();
                postOptions.clear();
                for(int i=1;i<=size;i++){
                    Problem problem = questionnaire.getProblem(i - 1);
                    ArrayList<String> selects = getSelectedOptionForProblem(i);
                    for(String s : selects){
                        int index = s.charAt(0) - 'A';
                        postOptions.add(problem.getOption(index).getOptionId());
                    }
                }
                SubmitQuestionnaireAsyncTask submitQuestionnaireAsyncTask = new SubmitQuestionnaireAsyncTask(QuestionnaireActivity.this,postOptions);
                submitQuestionnaireAsyncTask.execute();
            }
        });
    }

    //String question, String[] options, boolean isMultipleChoice
    private void addProblem(Problem problem) {
        ArrayList<Option> options = problem.getOptions();
        boolean isMultipleChoice = false;
        if(problem.getIsMultipleChoice()==1){
            isMultipleChoice = true;
        }
        String question = problem.getContent();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout problemLayout = new LinearLayout(this);
        problemLayout.setOrientation(LinearLayout.VERTICAL);
        problemLayout.setLayoutParams(layoutParams);

        TextView questionTextView = new TextView(this);
        questionTextView.setText(problem.getNum() + "." + question);
        problemLayout.addView(questionTextView);

        if (isMultipleChoice) {
            for (Option option : options) {
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText(option.getOptionNum() + option.getContent());
                problemLayout.addView(checkBox);
            }
        } else {
            RadioGroup radioGroup = new RadioGroup(this);
            for (Option option : options) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(option.getOptionNum() + option.getContent());
                radioGroup.addView(radioButton);
            }
            problemLayout.addView(radioGroup);
        }

        this.problemLayout.addView(problemLayout);
    }

    //获取选了哪些选项
    private ArrayList<String> getSelectedOptionForProblem(int problemIndex) {
        Problem problem = questionnaire.getProblem(problemIndex - 1);
        if(problem.getIsMultipleChoice() == 1){//多选题
            ArrayList<String> selectedOptions = new ArrayList<>();
            LinearLayout problemLayout = (LinearLayout) this.problemLayout.getChildAt(problemIndex - 1); // 获取指定问题的布局
            int optionCount = problemLayout.getChildCount(); // 获取问题选项的数量
            for (int i = 1; i < optionCount; i++) {
                View optionView = problemLayout.getChildAt(i);
                if (optionView instanceof RadioButton) {
                    RadioButton radioButton = (RadioButton) optionView;
                    if (radioButton.isChecked()) {
                        String optionText = radioButton.getText().toString();
                        selectedOptions.add(optionText);
                    }
                } else if (optionView instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) optionView;
                    if (checkBox.isChecked()) {
                        String optionText = checkBox.getText().toString();
                        selectedOptions.add(optionText.substring(0,1));
                    }
                }
            }
            return selectedOptions;
        }
        else{
            //单选题
            ArrayList<String> res = new ArrayList<>();
            LinearLayout problemLayout = (LinearLayout) this.problemLayout.getChildAt(problemIndex - 1); // 获取指定问题的布局
            RadioGroup radioGroup = (RadioGroup) problemLayout.getChildAt(problemLayout.getChildCount() - 1); // 获取问题的RadioGroup
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId(); // 获取被选中的单选按钮的ID

            if (checkedRadioButtonId != -1) {
                RadioButton checkedRadioButton = radioGroup.findViewById(checkedRadioButtonId); // 在RadioGroup中查找被选中的单选按钮
                res.add(checkedRadioButton.getText().toString().substring(0,1));
                return res; // 返回选中的选项文本
            }
            return null;
        }

    }

}