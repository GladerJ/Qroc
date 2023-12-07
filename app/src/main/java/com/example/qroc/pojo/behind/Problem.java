package com.example.qroc.pojo.behind;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class Problem implements Serializable {

    @JsonProperty("problemId")
    private Long problemId;

    @JsonProperty("questionnaireId")
    private Long questionnaireId;

    @JsonProperty("num")
    private Long num;

    @JsonProperty("content")
    private String content;

    @JsonProperty("isMultipleChoice")
    private int isMultipleChoice;

    @JsonProperty("options")
    private ArrayList<Option> options;

    public Long getProblemId() {
        return problemId;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "problemId=" + problemId +
                ", questionnaireId=" + questionnaireId +
                ", num=" + num +
                ", content='" + content + '\'' +
                ", isMultipleChoice=" + isMultipleChoice +
                ", options=" + options +
                '}';
    }

    public Problem(Long problemId, Long questionnaireId, Long num, String content, int isMultipleChoice, ArrayList<Option> options) {
        this.problemId = problemId;
        this.questionnaireId = questionnaireId;
        this.num = num;
        this.content = content;
        this.isMultipleChoice = isMultipleChoice;
        this.options = options;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
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

    public Problem(){
        options = new ArrayList<>();
    }
    public void addOption(Option option){
        options.add(option);
    }
    public Option getOption(int index){
        return options.get(index);
    }
}
