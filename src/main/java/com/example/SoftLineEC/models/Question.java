package com.example.SoftLineEC.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idQuestion;
    private String nameOfQuestion;
    private int score;
    @ManyToOne(optional = true)
    private Test testID;
    @OneToMany(mappedBy = "questionID", fetch = FetchType.EAGER)
    private Collection<AnswerOptions> tenants;

    public Question(String nameOfQuestion, int score, Test testID) {
        this.nameOfQuestion = nameOfQuestion;
        this.score = score;
        this.testID = testID;
    }
    public Question(){}

    public long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(long idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getNameOfQuestion() {
        return nameOfQuestion;
    }

    public void setNameOfQuestion(String nameOfQuestion) {
        this.nameOfQuestion = nameOfQuestion;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Test getTestID() {
        return testID;
    }

    public void setTestID(Test testID) {
        this.testID = testID;
    }

    public Collection<AnswerOptions> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<AnswerOptions> tenants) {
        this.tenants = tenants;
    }
}
