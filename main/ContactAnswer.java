package com.example.hp.qask;

public class ContactAnswer {

    private String Answers;
    public ContactAnswer(String Answers){
        this.setAnswer(Answers);
    }
    public String getAnswer(){
        return Answers;
    }
    public void setAnswer(String answer){
        Answers = answer;
    }
}
