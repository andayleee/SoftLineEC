package com.example.SoftLineEC.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class FormOfEducation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idFormOfEducation;
    private String typeOfEducation;

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
