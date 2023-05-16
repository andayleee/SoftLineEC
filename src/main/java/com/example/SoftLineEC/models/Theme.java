package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTheme;

    private String nameOfTheme;

    @JsonManagedReference
    @OneToMany(mappedBy = "courseTypeID", fetch = FetchType.EAGER)
    private Collection<Course> tenants;

    public Theme(String nameOfTheme) {
        this.nameOfTheme = nameOfTheme;
    }

    public Theme(){}

    public String getNameOfTheme() {
        return nameOfTheme;
    }

    public void setNameOfTheme(String nameOfTheme) {
        this.nameOfTheme = nameOfTheme;
    }

    public Collection<Course> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Course> tenants) {
        this.tenants = tenants;
    }
}
