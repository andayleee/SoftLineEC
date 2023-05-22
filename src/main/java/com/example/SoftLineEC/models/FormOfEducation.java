package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
/**
 * Класс-сущность для работы с формами обучения.
 */
@Entity
public class FormOfEducation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFormOfEducation;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 255,message = "Значение не может быть меньше 1 и больше 255 символов")
    private String typeOfEducation;
    @JsonManagedReference
    @OneToMany(mappedBy = "formOfEducationID", fetch = FetchType.EAGER)
    private Collection<Course> tenants;
    /**
     * Конструктор класса.
     * @param typeOfEducation название формы обучения
     */
    public FormOfEducation(String typeOfEducation) {
        this.typeOfEducation = typeOfEducation;
    }
    /**
     * Пустой конструктор класса.
     */
    public FormOfEducation() {}
    /**
     * Метод получения значения поля idFormOfEducation.
     * @return идентификатор формы обучения
     */
    public long getIdFormOfEducation() {
        return idFormOfEducation;
    }
    /**
     * Метод установки значения поля idFormOfEducation.
     * @param idFormOfEducation идентификатор формы обучения
     */
    public void setIdFormOfEducation(long idFormOfEducation) {
        this.idFormOfEducation = idFormOfEducation;
    }
    /**
     * Метод получения значения поля typeOfEducation.
     * @return название формы обучения
     */
    public String getTypeOfEducation() {
        return typeOfEducation;
    }
    /**
     * Метод установки значения поля typeOfEducation.
     * @param typeOfEducation название формы обучения
     */
    public void setTypeOfEducation(String typeOfEducation) {
        this.typeOfEducation = typeOfEducation;
    }
    /**
     * Метод получения значения поля tenants.
     * @return курсы, относящиеся к данной форме обучения
     */
    public Collection<Course> getTenants() {
        return tenants;
    }
    /**
     * Метод установки значения поля tenants.
     * @param tenants курсы, относящиеся к данной форме обучения
     */
    public void setTenants(Collection<Course> tenants) {
        this.tenants = tenants;
    }
}
