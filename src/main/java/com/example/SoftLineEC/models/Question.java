package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
/**
 * Класс-сущность для работы с вопросами тестов.
 */
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
    @JsonBackReference
    @ManyToOne(optional = true)
    private Test testID;
    @JsonManagedReference
    @OneToMany(mappedBy = "questionID", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Collection<AnswerOptions> tenants;
    /**
     * Конструктор класса.
     * @param nameOfQuestion текст вопроса
     * @param score стоимость правильного ответа на вопрос
     * @param testID тест, к которому относится вопрос
     */
    public Question(String nameOfQuestion, int score, Test testID) {
        this.nameOfQuestion = nameOfQuestion;
        this.score = score;
        this.testID = testID;
    }
    /**
     * Пустой конструктор класса.
     */
    public Question(){}
    /**
     * Метод получения значения поля idQuestion.
     * @return идентификатор вопроса
     */
    public long getIdQuestion() {
        return idQuestion;
    }
    /**
     * Метод установки значения поля idQuestion.
     * @param idQuestion идентификатор вопроса
     */
    public void setIdQuestion(long idQuestion) {
        this.idQuestion = idQuestion;
    }
    /**
     * Метод получения значения поля nameOfQuestion.
     * @return текст вопроса
     */
    public String getNameOfQuestion() {
        return nameOfQuestion;
    }
    /**
     * Метод установки значения поля nameOfQuestion.
     * @param nameOfQuestion текст вопроса
     */
    public void setNameOfQuestion(String nameOfQuestion) {
        this.nameOfQuestion = nameOfQuestion;
    }
    /**
     * Метод получения значения поля score.
     * @return стоимость правильного ответа на вопрос
     */
    public int getScore() {
        return score;
    }
    /**
     * Метод установки значения поля score.
     * @param score стоимость правильного ответа на вопрос
     */
    public void setScore(int score) {
        this.score = score;
    }
    /**
     * Метод получения значения поля testID.
     * @return тест, к которому относится вопрос
     */
    public Test getTestID() {
        return testID;
    }
    /**
     * Метод установки значения поля testID.
     * @param testID тест, к которому относится вопрос
     */
    public void setTestID(Test testID) {
        this.testID = testID;
    }
    /**
     * Метод получения значения поля tenants.
     * @return варианты ответов на данный вопрос
     */
    public Collection<AnswerOptions> getTenants() {
        return tenants;
    }
    /**
     * Метод установки значения поля tenants.
     * @param tenants варианты ответов на данный вопрос
     */
    public void setTenants(Collection<AnswerOptions> tenants) {
        this.tenants = tenants;
    }
}
