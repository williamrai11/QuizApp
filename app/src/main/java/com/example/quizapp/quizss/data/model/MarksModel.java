package com.example.quizapp.quizss.data.model;

public class MarksModel {
    private int score;
    private int incorrectAnswer;
    private String categoryName;
    private String date;

    public MarksModel(int score, int incorrectAnswer, String categoryName, String date) {
        this.score = score;
        this.incorrectAnswer = incorrectAnswer;
        this.categoryName = categoryName;
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIncorrectAnswer() {
        return incorrectAnswer;
    }

    public void setIncorrectAnswer(int incorrectAnswer) {
        this.incorrectAnswer = incorrectAnswer;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
