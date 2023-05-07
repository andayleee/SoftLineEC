package com.example.SoftLineEC.models;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class AnswerOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAnswerOptions;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 2000,message = "Значение не может быть меньше 1 и больше 2000 символов")
    private String content;
    private boolean valid;
    @JsonBackReference
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
