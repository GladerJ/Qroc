package com.example.qroc;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.qroc.pojo.Problem;

import java.util.ArrayList;

public class CreateQuestionnaire extends AppCompatActivity {

    private LinearLayout mainLayout;
    private Button createLayoutButton;
    private ScrollView scrollView;

    private Button save;

    private ArrayList<Problem> problems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        problems = new ArrayList<>();
        setContentView(R.layout.create_questionnaire);
        save = findViewById(R.id.save);

        mainLayout = findViewById(R.id.mainLayout);
        createLayoutButton = findViewById(R.id.createLayoutButton);

        scrollView = findViewById(R.id.scrollview);

        createLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDynamicLayout();
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
    }

    private int questionCount = 0; // 跟踪题目数量的变量

    private void createDynamicLayout() {
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

        Problem problem = Problem.create(newLayout, questionNumberTextView);

        // 创建问题输入框
        EditText questionEditText = new EditText(this);
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
        addOptionButtonParams.setMargins(margin, margin, margin, margin); // 设置上下左右内边距
        addOptionButton.setLayoutParams(addOptionButtonParams);
        addOptionButton.setText("+");
        buttonLayout.addView(addOptionButton);

        //减少选项按钮
        Button removeOptionButton = new Button(this);
        LinearLayout.LayoutParams removeOptionButtonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        removeOptionButtonParams.setMargins(margin, margin, margin, margin); // 设置上下左右内边距
        removeOptionButton.setLayoutParams(removeOptionButtonParams);
        removeOptionButton.setText("-");
        buttonLayout.addView(removeOptionButton);

        //删除题目
        Button removeProblemButton = new Button(this);
        LinearLayout.LayoutParams removeProblemButtonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        removeProblemButtonParams.setMargins(margin, margin, margin, margin); // 设置上下左右内边距
        removeProblemButton.setLayoutParams(removeOptionButtonParams);
        removeProblemButton.setText("删除此题");
        buttonLayout.addView(removeProblemButton);


        newLayout.addView(buttonLayout);


        // 增加题目数量
        questionCount++;

        // 将新的线性布局添加到主布局中
        mainLayout.removeView(createLayoutButton);
        mainLayout.addView(newLayout);
        mainLayout.addView(createLayoutButton);


        // 为增加选项的按钮添加点击事件
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                LinearLayout.LayoutParams optionEditTextParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1); // 使用weight属性让输入框填充剩余空间
                optionEditTextParams.setMargins(margin, 0, margin, 0); // 设置左右外边距
                optionEditText.setLayoutParams(optionEditTextParams);
                optionEditText.setPadding(margin, margin, margin, margin); // 设置上下左右内边距
                optionEditText.setHint("请输入选项");
                newLayout.addView(optionEditText);
                newLayout.removeView(buttonLayout);
                newLayout.addView(buttonLayout);
                problem.addOption(optionEditText, optionNumberTextView);


            }
        });

        removeOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (problem.getOptions().size() == 0) return;
                newLayout.removeView(problem.getOptions().get(problem.getOptions().size() - 1));
                newLayout.removeView(problem.getOptionsTv().get(problem.getOptionsTv().size() - 1));
                problem.removeOption();
            }
        });
        removeProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problems.remove(problem);
                for (int i = 0; i < problems.size(); i++) {
                    problems.get(i).getId().setText(i + 1 + "");
                }
                mainLayout.removeView(newLayout);
                questionCount--;
            }
        });

        problems.add(problem);
    }

}
