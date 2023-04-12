package com.example.SoftLineEC.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idQuestion;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 500,message = "Значение не может быть меньше 1 и больше 500 символов")
    private String nameOfQuestion;
    @Min(value=0, message="Стоимость не может быть меньше 0")
    @Max(value=99, message="Стоимость не может быть больше 99")
    private int score;
    @ManyToOne(optional = true)
    private Test testID;
    @OneToMany(mappedBy = "questionID", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
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
