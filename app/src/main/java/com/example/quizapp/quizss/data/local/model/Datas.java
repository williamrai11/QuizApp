package com.example.quizapp.quizss.data.local.model;

public class Datas {
    private String question;
    private String categoryName;
    private int dataId;

    public Datas(String question, String categoryName, int dataId) {
        this.question = question;
        this.categoryName = categoryName;
        this.dataId = dataId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }
}
