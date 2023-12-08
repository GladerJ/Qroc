package com.example.qroc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.qroc.handler.AiCreateQuestionnaireAsyncTask;
import com.example.qroc.handler.UpdateQuestionnaireAsyncTask;
import com.example.qroc.pojo.ProblemView;
import com.example.qroc.pojo.behind.Option;
import com.example.qroc.pojo.behind.Problem;
import com.example.qroc.pojo.behind.Questionnaire;
import com.example.qroc.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

public class CreateQuestionnaire extends AppCompatActivity {

    private LinearLayout mainLayout;
    private Button createLayoutButton;
    private ScrollView scrollView;

    private Button save;

    private ArrayList<ProblemView> problemViews;

    private EditText title;

    private String username = "lpl1234";

    private Result saveStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        problemViews = new ArrayList<>();
        setContentView(R.layout.create_questionnaire);
        save = findViewById(R.id.save);
        title = findViewById(R.id.q_title);
        Intent intent = getIntent();
        String flag = intent.getStringExtra("flag");
        String ai = intent.getStringExtra("ai");
        save.setOnClickListener(e -> {
            MMKVUtils.init(getFilesDir().getAbsolutePath() + "/tmp");
            username = MMKVUtils.getString("token");
            Questionnaire questionnaire = QuestionnaireUtil.createQuestionnaire(problemViews, title, username);
            String json = null;
            try {
                json = JsonUtils.objectToJson(questionnaire);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
            save.setEnabled(false);

            if (json != null) {
                String finalJson = json;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            saveStatus = JsonUtils.jsonToResult(PostRequest.post(RegisterUser.URL + "/saveQuestionnaire", finalJson));
                        } catch (JsonProcessingException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                thread.start();
                try {
                    thread.join();

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if(saveStatus.getCode() == 1){
                    //Toast.makeText(CreateQuestionnaire.this,"保存成功",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateQuestionnaire.this);
                    builder.setTitle("系统提示：").setMessage("保存成功！");
                    builder.setIcon(R.mipmap.ic_launcher_round);
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(CreateQuestionnaire.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                    /**注册成功后跳转到MainActivity页面
                     */

                }
                else{
                    Toast.makeText(CreateQuestionnaire.this,saveStatus.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            // 执行按钮点击的操作

            save.setEnabled(true);

        });

        mainLayout = findViewById(R.id.mainLayout);
        createLayoutButton = findViewById(R.id.createLayoutButton);

        scrollView = findViewById(R.id.scrollview);

        createLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProblem(null);
                mainLayout.removeView(save);
                mainLayout.addView(save);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });

        if(flag != null){
            UpdateQuestionnaireAsyncTask updateQuestionnaireAsyncTask = new UpdateQuestionnaireAsyncTask(CreateQuestionnaire.this,Long.parseLong(flag),mainLayout,save);
            updateQuestionnaireAsyncTask.execute();
        }
        if(ai != null){
            AiCreateQuestionnaireAsyncTask aiCreateQuestionnaireAsyncTask = new AiCreateQuestionnaireAsyncTask(CreateQuestionnaire.this,ai,mainLayout,save);
            aiCreateQuestionnaireAsyncTask.execute();
        }
    }

    public void setTitle(String title1){
        if(title1 != null){
            title.setText(title1);
        }
    }

    private int questionCount = 0; // 跟踪题目数量的变量

    public void addOption(Option option1,LinearLayout newLayout,LinearLayout buttonLayout,ProblemView problemView,int margin,CheckBox checkBox){
        // 创建选项序号的TextView
        TextView optionNumberTextView = new TextView(CreateQuestionnaire.this);
        optionNumberTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        optionNumberTextView.setText(Character.toString((char) ('A' + (newLayout.getChildCount() - 3) / 2)) + ". "); // 使用字母标号，从A开始
        newLayout.addView(optionNumberTextView);
        LinearLayout.LayoutParams optionNumberTextViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        optionNumberTextViewParams.setMargins(margin, margin, margin, 0); // 设置上下左右内边距
        optionNumberTextView.setLayoutParams(optionNumberTextViewParams);
        // 创建选项输入框
        EditText optionEditText = new EditText(CreateQuestionnaire.this);
        if(option1 != null){
            optionEditText.setText(option1.getContent());
        }
        LinearLayout.LayoutParams optionEditTextParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1); // 使用weight属性让输入框填充剩余空间
        optionEditTextParams.setMargins(margin, 0, margin, 0); // 设置左右外边距
        optionEditText.setLayoutParams(optionEditTextParams);
        optionEditText.setPadding(margin, margin, margin, margin); // 设置上下左右内边距
        optionEditText.setHint("请输入选项");
        newLayout.addView(optionEditText);
        newLayout.removeView(buttonLayout);
        newLayout.addView(buttonLayout);
        problemView.setLinearLayout(newLayout);
        problemView.addOption(optionEditText, optionNumberTextView);
        newLayout.removeView(checkBox);
        newLayout.addView(checkBox);
    }

    public void addProblem(Problem problem1){
        // 创建一个新的线性布局
        LinearLayout newLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = getResources().getDimensionPixelSize(R.dimen.layout_margin); // 获取间距的像素值
        layoutParams.setMargins(margin, margin, margin, margin); // 设置上下左右外边距
        newLayout.setLayoutParams(layoutParams);
        newLayout.setOrientation(LinearLayout.VERTICAL); // 设置为垂直方向布局
        newLayout.setBackgroundResource(R.drawable.border); // 设置边框背景

        // 创建题目序号的TextView
        TextView questionNumberTextView = new TextView(this);
        LinearLayout.LayoutParams questionNumberTextViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        questionNumberTextViewParams.setMargins(margin, margin, margin, 0); // 设置上下左右内边距
        questionNumberTextView.setLayoutParams(questionNumberTextViewParams);
        questionNumberTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium); // 设置字体大小为Medium
        questionNumberTextView.setText(questionCount + 1 + ". ");
        newLayout.addView(questionNumberTextView);

        ProblemView problemView = ProblemView.create(newLayout, questionNumberTextView);

        // 创建问题输入框
        EditText questionEditText = new EditText(this);
        if(problem1 != null)
            questionEditText.setText(problem1.getContent());
        LinearLayout.LayoutParams questionEditTextParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1); // 使用weight属性让输入框填充剩余空间
        questionEditTextParams.setMargins(margin, 0, margin, 0); // 设置左右外边距
        questionEditText.setLayoutParams(questionEditTextParams);
        questionEditText.setPadding(margin, margin, margin, margin); // 设置上下左右内边距
        questionEditText.setHint("请输入问题");
        newLayout.addView(questionEditText);

        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);



        // 创建增加选项的按钮
        Button addOptionButton = new Button(this);
        LinearLayout.LayoutParams addOptionButtonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addOptionButtonParams.setMargins(margin, 0, 0, margin); // 设置上下左右内边距
        addOptionButton.setLayoutParams(addOptionButtonParams);
        addOptionButton.setText("+");
        buttonLayout.addView(addOptionButton);

        //减少选项按钮
        Button removeOptionButton = new Button(this);
        LinearLayout.LayoutParams removeOptionButtonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        removeOptionButton.setLayoutParams(removeOptionButtonParams);
        removeOptionButtonParams.setMargins(margin, 0, 0, margin); // 设置上下左右内边距
        removeOptionButton.setText("-");
        buttonLayout.addView(removeOptionButton);


        //删除题目
        Button removeProblemButton = new Button(this);
        LinearLayout.LayoutParams removeProblemButtonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        removeProblemButtonParams.setMargins(margin, 0, 0, margin); // 设置上下左右内边距
        removeProblemButton.setLayoutParams(removeOptionButtonParams);
        removeProblemButton.setText("删除此题");
        buttonLayout.addView(removeProblemButton);

        //是否多选
        CheckBox checkBox = new CheckBox(this);
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        checkBox.setLayoutParams(checkBoxParams);
        checkBoxParams.setMargins(margin, 0, 0, margin);
        checkBox.setText("多选");


        newLayout.addView(buttonLayout);
        newLayout.addView(checkBox);

        // 增加题目数量
        questionCount++;

        // 将新的线性布局添加到主布局中
        mainLayout.removeView(createLayoutButton);
        mainLayout.addView(newLayout);
        mainLayout.addView(createLayoutButton);

        if(problem1 != null){
            ArrayList<Option> options = problem1.getOptions();
            options.stream().forEach(option -> {
                addOption(option,newLayout,buttonLayout,problemView,margin,checkBox);
            });
        }


        // 为增加选项的按钮添加点击事件
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOption(null,newLayout,buttonLayout,problemView,margin,checkBox);
            }
        });

        removeOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (problemView.getOptions().size() == 0) return;
                newLayout.removeView(problemView.getOptions().get(problemView.getOptions().size() - 1));
                newLayout.removeView(problemView.getOptionsTv().get(problemView.getOptionsTv().size() - 1));
                problemView.removeOption();
            }
        });
        removeProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problemViews.remove(problemView);
                for (int i = 0; i < problemViews.size(); i++) {
                    problemViews.get(i).getId().setText(i + 1 + "");
                }
                mainLayout.removeView(newLayout);
                questionCount--;
            }
        });

        problemViews.add(problemView);
    }



}
