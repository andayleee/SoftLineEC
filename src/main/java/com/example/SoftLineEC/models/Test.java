package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

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
    @OneToMany(mappedBy = "testID", fetch = FetchType.EAGER)
    private Collection<Question> tenants;

    public Test(String nameOfTest, Lecture lectureID) {
        this.nameOfTest = nameOfTest;
        this.lectureID = lectureID;
    }
    public Test(){}

    public long getIdTest() {
        return idTest;
    }

    public void setIdTest(long idTest) {
        this.idTest = idTest;
    }

    public String getNameOfTest() {
        return nameOfTest;
    }

    public void setNameOfTest(String nameOfTest) {
        this.nameOfTest = nameOfTest;
    }

    public Lecture getLectureID() {
        return lectureID;
    }

    public void setLectureID(Lecture lectureID) {
        this.lectureID = lectureID;
    }

    public Collection<Question> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Question> tenants) {
        this.tenants = tenants;
    }
}
