package com.example.SoftLineEC.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idTest;
    private String nameOfTest;
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
