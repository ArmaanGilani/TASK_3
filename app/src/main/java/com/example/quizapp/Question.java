package com.example.quizapp;

public class Question {

    String question, option1, option2, option3, option4;
    int answernumber;

    public Question(){}
    public Question(String question, String option1, String option2, String option3, String option4, int answernumber) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answernumber = answernumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getAnswernumber() {
        return answernumber;
    }

    public void setAnswernumber(int answernumber) {
        this.answernumber = answernumber;
    }
}