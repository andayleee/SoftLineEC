package com.example.SoftLineEC.models;
import javax.persistence.*;

@Entity
public class AnswerOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idAnswerOptions;
    private String content;
    private boolean valid;
    @ManyToOne(optional = true)
    private Question questionID;

    public AnswerOptions(String content, boolean valid, Question questionID) {
        this.content = content;
        this.valid = valid;
        this.questionID = questionID;
    }
    public AnswerOptions(){}

    public long getIdAnswerOptions() {
        return idAnswerOptions;
    }

    public void setIdAnswerOptions(long idAnswerOptions) {
        this.idAnswerOptions = idAnswerOptions;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Question getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Question questionID) {
        this.questionID = questionID;
    }
}
