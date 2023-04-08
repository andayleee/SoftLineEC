package com.example.SoftLineEC.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBlock;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 255,message = "Значение не может быть меньше 1 и больше 255 символов")
    private String nameOfBlock;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 9000,message = "Значение не может быть меньше 1 и больше 9000 символов")
    private String description;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 255,message = "Значение не может быть меньше 1 и больше 255 символов")
    private String duration;
    @JsonBackReference
    @ManyToOne(optional = true)
    private Course courseID;
    @JsonManagedReference
    @OneToMany(mappedBy = "blockID", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Collection<Lecture> tenants;

    public Block(String nameOfBlock, String description, String duration, Course courseID) {
        this.nameOfBlock = nameOfBlock;
        this.description = description;
        this.duration = duration;
        this.courseID = courseID;
    }

    public Block (){}

    public long getIdBlock() {
        return idBlock;
    }

    public void setIdBlock(long idBlock) {
        this.idBlock = idBlock;
    }

    public String getNameOfBlock() {
        return nameOfBlock;
    }

    public void setNameOfBlock(String nameOfBlock) {
        this.nameOfBlock = nameOfBlock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Course getCourseID() {
        return courseID;
    }

    public void setCourseID(Course courseID) {
        this.courseID = courseID;
    }

    public Collection<Lecture> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Lecture> tenants) {
        this.tenants = tenants;
    }
}
