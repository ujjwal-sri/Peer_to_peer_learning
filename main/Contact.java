package com.example.hp.qask;

public class Contact {
    private String Question,Answer;
    public Contact(String Question,String Answer){
        this.setQuestion(Question);
        this.setAnswer(Answer);
    }
    public String getQuestion(){
        return Question;
    }
    public void setQuestion(String question){
        Question = question;
    }
    public String getAnswer(){
        return Answer;
    }
    public void setAnswer(String answer){
        Answer = answer;
    }

}
