package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
/**
 * Класс-сущность для работы с тестами.
 */
@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTest;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 255,message = "Значение не может быть меньше 1 и больше 255 символов")
    private String nameOfTest;
    @JsonBackReference
    @ManyToOne(optional = true)
    private Lecture lectureID;
    @JsonManagedReference
    @OneToMany(mappedBy = "testID", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Collection<Question> tenants;
    /**
     * Конструктор класса.
     * @param nameOfTest название теста
     * @param lectureID лекция, к которой относится тест
     */
    public Test(String nameOfTest, Lecture lectureID) {
        this.nameOfTest = nameOfTest;
        this.lectureID = lectureID;
    }
    /**
     * Пустой конструктор класса.
     */
    public Test(){}
    /**
     * Метод получения значения поля idTest.
     * @return идентификатор теста
     */
    public long getIdTest() {
        return idTest;
    }
    /**
     * Метод установки значения поля idTest.
     * @param idTest идентификатор теста
     */
    public void setIdTest(long idTest) {
        this.idTest = idTest;
    }
    /**
     * Метод получения значения поля nameOfTest.
     * @return название теста
     */
    public String getNameOfTest() {
        return nameOfTest;
    }
    /**
     * Метод установки значения поля nameOfTest.
     * @param nameOfTest название теста
     */
    public void setNameOfTest(String nameOfTest) {
        this.nameOfTest = nameOfTest;
    }
    /**
     * Метод получения значения поля lectureID.
     * @return лекция, к которой относится тест
     */
    public Lecture getLectureID() {
        return lectureID;
    }
    /**
     * Метод установки значения поля lectureID.
     * @param lectureID лекция, к которой относится тест
     */
    public void setLectureID(Lecture lectureID) {
        this.lectureID = lectureID;
    }
    /**
     * Метод получения значения поля tenants.
     * @return вопросы, составляющие данный тест
     */
    public Collection<Question> getTenants() {
        return tenants;
    }
    /**
     * Метод установки значения поля tenants.
     * @param tenants вопросы, составляющие данный тест
     */
    public void setTenants(Collection<Question> tenants) {
        this.tenants = tenants;
    }
}
