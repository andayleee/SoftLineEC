package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
/**
 * Класс-сущность для работы с темами курсов.
 */
@Entity
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTheme;

    private String nameOfTheme;

    @JsonManagedReference
    @OneToMany(mappedBy = "courseTypeID", fetch = FetchType.EAGER)
    private Collection<Course> tenants;
    /**
     * Конструктор класса.
     * @param nameOfTheme название темы
     */
    public Theme(String nameOfTheme) {
        this.nameOfTheme = nameOfTheme;
    }
    /**
     * Пустой конструктор класса.
     */
    public Theme(){}
    /**
     * Метод получения значения поля nameOfTheme.
     * @return название темы
     */
    public String getNameOfTheme() {
        return nameOfTheme;
    }
    /**
     * Метод установки значения поля nameOfTheme.
     * @param nameOfTheme название темы
     */
    public void setNameOfTheme(String nameOfTheme) {
        this.nameOfTheme = nameOfTheme;
    }
    /**
     * Метод получения значения поля tenants.
     * @return курсы, связанные с данной темой
     */
    public Collection<Course> getTenants() {
        return tenants;
    }
    /**
     * Метод установки значения поля tenants.
     * @param tenants курсы, связанные с данной темой
     */
    public void setTenants(Collection<Course> tenants) {
        this.tenants = tenants;
    }
}
