package com.example.SoftLineEC.models;

import javax.persistence.*;

@Entity
public class CourseType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idCourseType;
    private String nameOfCourseType;

    public CourseType(String nameOfCourseType) {
        this.nameOfCourseType = nameOfCourseType;
    }

    public CourseType(){}

    public long getIdCourseType() {
        return idCourseType;
    }

    public void setIdCourseType(long idCourseType) {
        this.idCourseType = idCourseType;
    }

    public String getNameOfCourseType() {
        return nameOfCourseType;
    }

    public void setNameOfCourseType(String nameOfCourseType) {
        this.nameOfCourseType = nameOfCourseType;
    }
}
