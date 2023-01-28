package com.example.SoftLineEC.models;
import javax.persistence.*;

@Entity
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idBlock;
    private String nameOfBlock;
    private String description;
    private String duration;
    @ManyToOne(optional = true)
    private Course courseID;

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
}
