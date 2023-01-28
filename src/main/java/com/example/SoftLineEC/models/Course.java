package com.example.SoftLineEC.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idCourse;
    private String nameOfCourse;
    private Date dateOfCreation;
    private String description;
    private String resources;
    private String goal;
    private String tasks;
    private String categoriesOfStudents;
    @ManyToOne(optional = true)
    private CourseType courseTypeID;
    @ManyToOne(optional = true)
    private FormOfEducation formOfEducationID;
    @OneToMany(mappedBy = "courseID", fetch = FetchType.EAGER)
    private Collection<Block> tenants;

    public Course(String nameOfCourse, Date dateOfCreation, String description, String resources, String goal, String tasks, String categoriesOfStudents, CourseType courseTypeID, FormOfEducation formOfEducationID) {
        this.nameOfCourse = nameOfCourse;
        this.dateOfCreation = dateOfCreation;
        this.description = description;
        this.resources = resources;
        this.goal = goal;
        this.tasks = tasks;
        this.categoriesOfStudents = categoriesOfStudents;
        this.courseTypeID = courseTypeID;
        this.formOfEducationID = formOfEducationID;
    }

    public Course(){}

    public long getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(long idCourse) {
        this.idCourse = idCourse;
    }

    public String getNameOfCourse() {
        return nameOfCourse;
    }

    public void setNameOfCourse(String nameOfCourse) {
        this.nameOfCourse = nameOfCourse;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public String getCategoriesOfStudents() {
        return categoriesOfStudents;
    }

    public void setCategoriesOfStudents(String categoriesOfStudents) {
        this.categoriesOfStudents = categoriesOfStudents;
    }

    public CourseType getCourseTypeID() {
        return courseTypeID;
    }

    public void setCourseTypeID(CourseType courseTypeID) {
        this.courseTypeID = courseTypeID;
    }

    public FormOfEducation getFormOfEducationID() {
        return formOfEducationID;
    }

    public void setFormOfEducationID(FormOfEducation formOfEducationID) {
        this.formOfEducationID = formOfEducationID;
    }

    public Collection<Block> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Block> tenants) {
        this.tenants = tenants;
    }
}
