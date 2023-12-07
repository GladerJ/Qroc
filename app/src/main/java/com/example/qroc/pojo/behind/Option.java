package com.example.qroc.pojo.behind;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class Option implements Serializable {
    @Override
    public String toString() {
        return "Option{" +
                "optionId=" + optionId +
                ", optionNum='" + optionNum + '\'' +
                ", content='" + content + '\'' +
                ", problemId=" + problemId +
                ", count=" + count +
                '}';
    }

    @JsonProperty("optionId")
    private Long optionId;

    @JsonProperty("optionNum")
    private String optionNum;

    @JsonProperty("content")
    private String content;

    @JsonProperty("problemId")
    private Long problemId;

    @JsonProperty("count")
    private Long count;

    public Option() {
    }

    public Option(Long optionId, String optionNum, String content, Long problemId, Long count) {
        this.optionId = optionId;
        this.optionNum = optionNum;
        this.content = content;
        this.problemId = problemId;
        this.count = count;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
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

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
