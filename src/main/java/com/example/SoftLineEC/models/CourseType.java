package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
/**
 * Класс-сущность для работы с типами курсов.
 */
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
    /**
     * Конструктор класса.
     * @param nameOfCourseType название типа курса
     */
    public CourseType(String nameOfCourseType) {
        this.nameOfCourseType = nameOfCourseType;
    }
    /**
     * Пустой конструктор класса.
     */
    public CourseType(){}
    /**
     * Метод получения значения поля idCourseType.
     * @return идентификатор типа курса
     */
    public long getIdCourseType() {
        return idCourseType;
    }
    /**
     * Метод установки значения поля idCourseType.
     * @param idCourseType идентификатор типа курса
     */
    public void setIdCourseType(long idCourseType) {
        this.idCourseType = idCourseType;
    }
    /**
     * Метод получения значения поля nameOfCourseType.
     * @return название типа курса
     */
    public String getNameOfCourseType() {
        return nameOfCourseType;
    }
    /**
     * Метод установки значения поля nameOfCourseType.
     * @param nameOfCourseType название типа курса
     */
    public void setNameOfCourseType(String nameOfCourseType) {
        this.nameOfCourseType = nameOfCourseType;
    }
    /**
     * Метод получения значения поля tenants.
     * @return курсы, относящиеся к данному типу
     */
    public Collection<Course> getTenants() {
        return tenants;
    }
    /**
     * Метод установки значения поля tenants.
     * @param tenants курсы, относящиеся к данному типу
     */
    public void setTenants(Collection<Course> tenants) {
        this.tenants = tenants;
    }
}
