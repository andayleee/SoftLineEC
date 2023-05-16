package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class UsersCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsersCourses;

    @JsonBackReference
    @ManyToOne(optional = true)
    private User userID;

    @JsonBackReference
    @ManyToOne(optional = true)
    private Course courseID;

    private boolean completeness;

    private String certificateNumber;

    private String passedLectures;

    private String testResults;

    public UsersCourses(User userID, Course courseID, boolean completeness, String certificateNumber, String passedLectures, String testResults) {
        this.userID = userID;
        this.courseID = courseID;
        this.completeness = completeness;
        this.certificateNumber = certificateNumber;
        this.passedLectures = passedLectures;
        this.testResults = testResults;
    }

    public UsersCourses(){}

    public long getIdUsersCourses() {
        return idUsersCourses;
    }

    public void setIdUsersCourses(long idUsersCourses) {
        this.idUsersCourses = idUsersCourses;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Course getCourseID() {
        return courseID;
    }

    public void setCourseID(Course courseID) {
        this.courseID = courseID;
    }

    public boolean isCompleteness() {
        return completeness;
    }

    public void setCompleteness(boolean completeness) {
        this.completeness = completeness;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getPassedLectures() {
        return passedLectures;
    }

    public void setPassedLectures(String passedLectures) {
        this.passedLectures = passedLectures;
    }

    public String getTestResults() {
        return testResults;
    }

    public void setTestResults(String testResults) {
        this.testResults = testResults;
    }
}
