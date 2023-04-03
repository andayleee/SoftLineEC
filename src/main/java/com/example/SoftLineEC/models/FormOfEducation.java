package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class FormOfEducation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idFormOfEducation;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 255,message = "Значение не может быть меньше 1 и больше 255 символов")
    private String typeOfEducation;
    @JsonManagedReference
    @OneToMany(mappedBy = "formOfEducationID", fetch = FetchType.EAGER)
    private Collection<Course> tenants;

    public FormOfEducation(String typeOfEducation) {
        this.typeOfEducation = typeOfEducation;
    }

    public FormOfEducation() {}

    public long getIdFormOfEducation() {
        return idFormOfEducation;
    }

    public void setIdFormOfEducation(long idFormOfEducation) {
        this.idFormOfEducation = idFormOfEducation;
    }

    public String getTypeOfEducation() {
        return typeOfEducation;
    }

    public void setTypeOfEducation(String typeOfEducation) {
        this.typeOfEducation = typeOfEducation;
    }
    public Collection<Course> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Course> tenants) {
        this.tenants = tenants;
    }
}
