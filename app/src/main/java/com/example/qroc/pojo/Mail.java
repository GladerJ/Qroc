package com.example.qroc.pojo;

public class Mail {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Mail() {
    }

    public Mail(String code, String email) {
        this.code = code;
        this.email = email;
    }

    private String email;
}