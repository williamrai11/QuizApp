package com.example.quizapp.quizss.data.local.model;

public class MarksModel {
    private int score;
    private int incorrectAnswer;
    private String categoryName;
    private String date;
    private String difficulty;
    private long part;

    public MarksModel(int score, int incorrectAnswer, String categoryName, String date,String difficulty,long part) {
        this.score = score;
        this.incorrectAnswer = incorrectAnswer;
        this.categoryName = categoryName;
        this.date = date;
        this.difficulty = difficulty;
        this.part = part;
    }

    public MarksModel(int correctAns, int incorrectAnswer,String difficulty){
        this.score = correctAns;
        this.incorrectAnswer = incorrectAnswer;
        this.difficulty = difficulty;

    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public long getPart() {
        return part;
    }

    public void setPart(long part) {
        this.part = part;
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
