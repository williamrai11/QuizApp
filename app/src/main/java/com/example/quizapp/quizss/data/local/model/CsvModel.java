package com.example.quizapp.quizss.data.local.model;

public class CsvModel {
    private String index;
    private String section;
    private String difficulty;
    private String part;
    private String question;
    private String answer;
    private String explanation;
    private String choiceOne;
    private String choiceTwo;


    public CsvModel(String index,String section,String difficulty,String part,String question,
                    String answer,String explanation, String choiceOne,
                    String choiceTwo) {
        this.index = index;
        this.section = section;
        this.difficulty = difficulty;
        this.part = part;
        this.question = question;
        this.answer = answer;
        this.explanation = explanation;
        this.choiceOne = choiceOne;
        this.choiceTwo = choiceTwo;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getIndex() {
        return index;
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
