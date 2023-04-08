package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPhoto;
    private String photoPath;
    @JsonBackReference
    @ManyToOne(optional = true)
    private Lecture lectureID;

    public Photo(String photoPath, Lecture lectureID) {
        this.photoPath = photoPath;
        this.lectureID = lectureID;
    }
    public Photo(){}

    public long getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(long idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Lecture getLectureID() {
        return lectureID;
    }

    public void setLectureID(Lecture lectureID) {
        this.lectureID = lectureID;
    }
}
