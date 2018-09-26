package com.rakib.initialassessment.model;

public class Question {
    long id;
    String question;
    String optionA;
    String optionB;
    String optionC;
    String correct;


    public Question() {
    }

    public Question(long id, String question, String optionA, String optionB, String optionC, String correct) {
        this.id = id;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.correct = correct;
    }

    public long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getCorrect() {
        return correct;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }
}
