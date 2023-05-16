package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCourse;
    @Column(unique = true)
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1, max = 255, message = "Значение не может быть больше 255 символов")
    private String nameOfCourse;
    private Date dateOfCreation;
    @Size(max = 5000, message = "Значение не может быть меньше 1 и больше 5000 символов")
    private String description;
    @Size(max = 1000, message = "Значение не может быть меньше 1 и больше 1000 символов")
    private String resources;
    @Size(max = 5000, message = "Значение не может быть меньше 1 и больше 5000 символов")
    private String goal;
    @Size(max = 800, message = "Значение не может быть меньше 1 и больше 800 символов")
    private String tasks;
    @Size(max = 5000, message = "Значение не может быть меньше 1 и больше 5000 символов")
    private String categoriesOfStudents;
    @JsonBackReference
    @ManyToOne(optional = true)
    private CourseType courseTypeID;
    @JsonBackReference
    @ManyToOne(optional = true)
    private FormOfEducation formOfEducationID;

    @JsonBackReference
    @ManyToOne(optional = true)
    private User userID;

    @JsonBackReference
    @ManyToOne(optional = true)
    private Theme themeID;
    @JsonManagedReference
    @OneToMany(mappedBy = "courseID", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Block> tenants;

    @JsonManagedReference
    @OneToMany(mappedBy = "courseID", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<UsersCourses> tenants2;

    public Course(String nameOfCourse, Date dateOfCreation, String description, String resources, String goal, String tasks, String categoriesOfStudents, CourseType courseTypeID, FormOfEducation formOfEducationID, User userID, Theme themeID) {
        this.nameOfCourse = nameOfCourse;
        this.dateOfCreation = dateOfCreation;
        this.description = description;
        this.resources = resources;
        this.goal = goal;
        this.tasks = tasks;
        this.categoriesOfStudents = categoriesOfStudents;
        this.courseTypeID = courseTypeID;
        this.formOfEducationID = formOfEducationID;
        this.userID = userID;
        this.themeID = themeID;
    }

    public Course() {
    }

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

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Theme getThemeID() {
        return themeID;
    }

    public void setThemeID(Theme themeID) {
        this.themeID = themeID;
    }

    public Collection<Block> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Block> tenants) {
        this.tenants = tenants;
    }

    public Collection<UsersCourses> getTenants2() {
        return tenants2;
    }

    public void setTenants2(Collection<UsersCourses> tenants2) {
        this.tenants2 = tenants2;
    }
}
