package com.example.qroc.util;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.qroc.pojo.ProblemView;
import com.example.qroc.pojo.behind.Option;
import com.example.qroc.pojo.behind.Problem;
import com.example.qroc.pojo.behind.Questionnaire;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuestionnaireUtil {
    public static Questionnaire createQuestionnaire(ArrayList<ProblemView> problemViews, EditText title,String username) {
        Questionnaire questionnaire = new Questionnaire();
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.format(now);
        questionnaire.setCreateTime(formattedDateTime);
        questionnaire.setUpdateTime(formattedDateTime);
        questionnaire.setTitle(title.getText().toString());
        problemViews.stream().forEach(problemView -> {
            Problem problem = new Problem();
            EditText editText = (EditText) problemView.getLinearLayout().getChildAt(1);
            problem.setContent(editText.getText().toString());
            TextView textView = (TextView) problemView.getLinearLayout().getChildAt(0);
            String tmp = textView.getText().toString();
            tmp = tmp.replace('.',' ');
            problem.setNum(Integer.parseInt(tmp.trim()));
            ArrayList<EditText> editTexts = problemView.getOptions();
            ArrayList<TextView> textViews = problemView.getOptionsTv();
            LinearLayout linearLayout = problemView.getLinearLayout();
            CheckBox checkBox = (CheckBox) linearLayout.getChildAt(linearLayout.getChildCount() - 1);
            if (checkBox.isChecked()) {
                problem.setIsMultipleChoice(1);
            } else {
                problem.setIsMultipleChoice(0);
            }
            for (int i = 0; i < editTexts.size(); i++) {
                Option option = new Option();
                option.setOptionNum(textViews.get(i).getText().toString());
                option.setContent(editTexts.get(i).getText().toString());
                problem.addOption(option);
            }
            questionnaire.addProblem(problem);
            questionnaire.setUsername(username);
        });
        return questionnaire;
    }
}
