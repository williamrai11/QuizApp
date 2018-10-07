package com.example.quizapp.quizss.data.model;

public class InsertOneCsv {
    private int id;
    private int currentQuestion;
    private int correctAnswer;
    private String categoryName;

    public InsertOneCsv(int currentQuestion, int correctAnswer, String categoryName) {
        this.currentQuestion = currentQuestion;
        this.correctAnswer = correctAnswer;
        this.categoryName = categoryName;
    }

    public InsertOneCsv(int id, int currentQuestion, int correctAnswer, String categoryName) {
        this.id = id;
        this.currentQuestion = currentQuestion;
        this.correctAnswer = correctAnswer;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
