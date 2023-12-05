package com.example.qroc.pojo.behind;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Questionnaire {
    //问卷ID
    private Integer questionnaireId;
    //问卷标题
    private String title;

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    //填写问卷的人数
    private Integer people;

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
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

    public Questionnaire(Integer questionnaireId, String title, Integer people, String createTime, String updateTime, String username, ArrayList<Problem> problems) {
        this.questionnaireId = questionnaireId;
        this.title = title;
        this.people = people;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.username = username;
        this.problems = problems;
    }

    //创建时间
    private String createTime;
    //更新修改时间
    private String updateTime;
    //所属用户
    private String username;
    //问题集合
    private ArrayList<Problem> problems;

    public Questionnaire(){
        problems = new ArrayList<>();
    }

    public void addProblem(Problem problem){
        problems.add(problem);
    }
}
