package com.example.qroc.pojo.behind;

import java.util.ArrayList;

public class Problem {
    //问题ID
    private Integer problemId;
    //所属问卷ID
    private Integer questionnaireId;
    //问题序号
    private Integer num;

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsMultipleChoice() {
        return isMultipleChoice;
    }

    public void setIsMultipleChoice(int isMultipleChoice) {
        this.isMultipleChoice = isMultipleChoice;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public Problem(Integer problemId, Integer questionnaireId, Integer num, String content, int isMultipleChoice, ArrayList<Option> options) {
        this.problemId = problemId;
        this.questionnaireId = questionnaireId;
        this.num = num;
        this.content = content;
        this.isMultipleChoice = isMultipleChoice;
        this.options = options;
    }

    //问题内容
    private String content;
    //是否是多选 1是，0否
    private int isMultipleChoice;
    //选项集合
    public Problem(){
        options = new ArrayList<>();
    }
    private ArrayList<Option> options;
    public void addOption(Option option){
        options.add(option);
    }
}
