package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
/**
 * Класс-сущность для работы с фотографиями.
 */
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPhoto;
    private String photoPath;
    @JsonBackReference
    @ManyToOne(optional = true)
    private Lecture lectureID;
    /**
     * Конструктор класса.
     * @param photoPath путь к файлу с фотографией
     * @param lectureID лекция, к которой относится фотография
     */
    public Photo(String photoPath, Lecture lectureID) {
        this.photoPath = photoPath;
        this.lectureID = lectureID;
    }
    /**
     * Пустой конструктор класса.
     */
    public Photo(){}
    /**
     * Метод получения значения поля idPhoto.
     * @return идентификатор фотографии
     */
    public long getIdPhoto() {
        return idPhoto;
    }
    /**
     * Метод установки значения поля idPhoto.
     * @param idPhoto идентификатор фотографии
     */
    public void setIdPhoto(long idPhoto) {
        this.idPhoto = idPhoto;
    }
    /**
     * Метод получения значения поля photoPath.
     * @return путь к файлу с фотографией
     */
    public String getPhotoPath() {
        return photoPath;
    }
    /**
     * Метод установки значения поля photoPath.
     * @param photoPath путь к файлу с фотографией
     */
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
    /**
     * Метод получения значения поля lectureID.
     * @return лекция, к которой относится фотография
     */
    public Lecture getLectureID() {
        return lectureID;
    }
    /**
     * Метод установки значения поля lectureID.
     * @param lectureID лекция, к которой относится фотография
     */
    public void setLectureID(Lecture lectureID) {
        this.lectureID = lectureID;
    }
}
