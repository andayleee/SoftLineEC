package com.example.SoftLineEC.models;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
/**
 * Класс-сущность для работы с вариантами ответов на вопросы.
 */
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
    /**
     * Конструктор класса.
     * @param content текст варианта ответа
     * @param valid признак правильности ответа
     * @param questionID вопрос, к которому относится вариант ответа
     */
    public AnswerOptions(String content, boolean valid, Question questionID) {
        this.content = content;
        this.valid = valid;
        this.questionID = questionID;
    }
    /**
     * Пустой конструктор класса.
     */
    public AnswerOptions(){}
    /**
     * Метод получения значения поля idAnswerOptions.
     * @return идентификатор варианта ответа
     */
    public long getIdAnswerOptions() {
        return idAnswerOptions;
    }
    /**
     * Метод установки значения поля idAnswerOptions.
     * @param idAnswerOptions идентификатор варианта ответа
     */
    public void setIdAnswerOptions(long idAnswerOptions) {
        this.idAnswerOptions = idAnswerOptions;
    }
    /**
     * Метод получения значения поля content.
     * @return текст варианта ответа
     */
    public String getContent() {
        return content;
    }
    /**
     * Метод установки значения поля content.
     * @param content текст варианта ответа
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * Метод получения значения поля valid.
     * @return признак правильности ответа
     */
    public boolean isValid() {
        return valid;
    }
    /**
     * Метод установки значения поля valid.
     * @param valid признак правильности ответа
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    /**
     * Метод получения значения поля questionID.
     * @return вопрос, к которому относится вариант ответа
     */
    public Question getQuestionID() {
        return questionID;
    }
    /**
     * Метод установки значения поля questionID.
     * @param questionID вопрос, к которому относится вариант ответа
     */
    public void setQuestionID(Question questionID) {
        this.questionID = questionID;
    }
}
