package com.example.quizapp.quizss.data.model;

public class QueryOneCsv {
    private String section;
    private String question;
    private String answer;
    private String explanation;
    private String choice_one;
    private String choice_two;

    public QueryOneCsv(String section, String question, String answer,  String explanation, String choice_one, String choice_two) {
        this.section = section;
        this.question = question;
        this.answer = answer;
        this.explanation = explanation;
        this.choice_one = choice_one;
        this.choice_two = choice_two;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getChoice_one() {
        return choice_one;
    }

    public void setChoice_one(String choice_one) {
        this.choice_one = choice_one;
    }

    public String getChoice_two() {
        return choice_two;
    }

    public void setChoice_two(String choice_two) {
        this.choice_two = choice_two;
    }
}
