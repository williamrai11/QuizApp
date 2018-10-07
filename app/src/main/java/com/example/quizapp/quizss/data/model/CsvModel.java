package com.example.quizapp.quizss.data.model;

public class CsvModel {
    private String section;
    private String question;
    private String answer;
    private String explanation;
    private String choiceOne;
    private String choiceTwo;


    public CsvModel(String section, String question,
                    String answer,String explanation, String choiceOne,
                    String choiceTwo) {
        this.section = section;
        this.question = question;
        this.answer = answer;
        this.explanation = explanation;
        this.choiceOne = choiceOne;
        this.choiceTwo = choiceTwo;
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

    public String getChoiceOne() {
        return choiceOne;
    }

    public void setChoiceOne(String choiceOne) {
        this.choiceOne = choiceOne;
    }

    public String getChoiceTwo() {
        return choiceTwo;
    }

    public void setChoiceTwo(String choiceTwo) {
        this.choiceTwo = choiceTwo;
    }



    @Override
    public String toString() {
        return "CsvModel{" +
                ", section='" + section + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", explanation='" + explanation + '\'' +
                ", choiceOne='" + choiceOne + '\'' +
                ", choiceTwo='" + choiceTwo + '\'' +
                '}';
    }
}
