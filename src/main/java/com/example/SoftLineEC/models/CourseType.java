package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

@Entity
public class CourseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCourseType;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 255,message = "Значение не может быть меньше 1 и больше 255 символов")
    private String nameOfCourseType;
    @JsonManagedReference
    @OneToMany(mappedBy = "courseTypeID", fetch = FetchType.EAGER)
    private Collection<Course> tenants;

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

    public Collection<Course> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Course> tenants) {
        this.tenants = tenants;
    }
}
