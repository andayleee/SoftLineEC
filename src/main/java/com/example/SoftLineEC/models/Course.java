package com.example.SoftLineEC.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idCourse;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 255,message = "Значение не может быть больше 255 символов")
    private String nameOfCourse;
//    @NotEmpty(message = "Значение не может быть пустым")
//    @PastOrPresent(message = "Значение должно представлять сегодняшнюю или прошедшую дату")
    private Date dateOfCreation;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 5000,message = "Значение не может быть меньше 1 и больше 5000 символов")
    private String description;
    @Size(min = 1,max = 1000,message = "Значение не может быть меньше 1 и больше 1000 символов")
    @NotBlank(message = "Значение не может быть пустым")
    private String resources;
    @Size(min = 1,max = 5000,message = "Значение не может быть меньше 1 и больше 5000 символов")
    @NotBlank(message = "Значение не может быть пустым")
    private String goal;
    @Size(min = 1,max = 800,message = "Значение не может быть меньше 1 и больше 800 символов")
    @NotBlank(message = "Значение не может быть пустым")
    private String tasks;
    @Size(min = 1,max = 5000,message = "Значение не может быть меньше 1 и больше 5000 символов")
    @NotBlank(message = "Значение не может быть пустым")
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
