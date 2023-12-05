package com.example.qroc.pojo.behind;


public class Option {
    //选项ID
    private Integer optionId;
    //选项号，如ABCD...
    private String optionNum;
    //选项内容
    private String content;
    //所属问题ID
    private Integer problemId;

    //多少人选过此选项
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Option() {
    }

    public Option(Integer optionId, String optionNum, String content, Integer problemId, Integer count) {
        this.optionId = optionId;
        this.optionNum = optionNum;
        this.content = content;
        this.problemId = problemId;
        this.count = count;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getOptionNum() {
        return optionNum;
    }

    public void setOptionNum(String optionNum) {
        this.optionNum = optionNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }
}
