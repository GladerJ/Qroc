package com.example.qroc.pojo.behind;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class Questionnaire implements Serializable {
    @Override
    public String toString() {
        return "Questionnaire{" +
                "questionnaireId=" + questionnaireId +
                ", title='" + title + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", username='" + username + '\'' +
                ", problems=" + problems +
                ", people=" + people +
                '}';
    }

    public Questionnaire(Long questionnaireId, String title, String createTime, String updateTime, String username, ArrayList<Problem> problems, Long people) {
        this.questionnaireId = questionnaireId;
        this.title = title;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.username = username;
        this.problems = problems;
        this.people = people;
    }

    @JsonProperty("questionnaireId")
    private Long questionnaireId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("createTime")
    private String createTime;

    @JsonProperty("updateTime")
    private String updateTime;

    @JsonProperty("username")
    private String username;

    @JsonProperty("problems")
    private ArrayList<Problem> problems  = new ArrayList<>();;

    @JsonProperty("people")
    private Long people;

    public Questionnaire(){

    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Problem> getProblems() {
        return problems;
    }

    public void setProblems(ArrayList<Problem> problems) {
        this.problems = problems;
    }

    public Long getPeople() {
        return people;
    }

    public void setPeople(Long people) {
        this.people = people;
    }

    public void addProblem(Problem problem){
        problems.add(problem);
    }

    public Problem getProblem(int index){
        return problems.get(index);
    }
}
