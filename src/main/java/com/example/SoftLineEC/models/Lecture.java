package com.example.SoftLineEC.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
/**
 * Класс-сущность для работы с лекциями.
 */
@Entity
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLecture;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 255,message = "Значение не может быть меньше 1 и больше 255 символов")
    private String nameOfLecture;

    @Size(max = 5000,message = "Значение не может быть меньше 1 и больше 5000 символов")
    private String description;
    @Size(max = 5000,message = "Значение не может быть меньше 1 и больше 5000 символов")
    private String content;

    @Size(max = 2000,message = "Значение не может быть меньше 1 и больше 2000 символов")
    private String additionalLiterature;
    @JsonBackReference
    @ManyToOne(optional = true)
    private Block blockID;
    @JsonManagedReference
    @OneToMany(mappedBy = "lectureID", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Collection<Test> tenants;
    @JsonManagedReference
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "lectureID", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Collection<Photo> tenants2;
    /**
     * Конструктор класса.
     * @param nameOfLecture название лекции
     * @param description описание лекции
     * @param content содержание лекции
     * @param additionalLiterature дополнительная литература по лекции
     * @param blockID блок, к которому относится лекция
     */
    public Lecture(String nameOfLecture, String description, String content, String additionalLiterature, Block blockID) {
        this.nameOfLecture = nameOfLecture;
        this.description = description;
        this.content = content;
        this.additionalLiterature = additionalLiterature;
        this.blockID = blockID;
    }
    /**
     * Пустой конструктор класса.
     */
    public Lecture(){}

    public long getIdLecture() {
        return idLecture;
    }

    public void setIdLecture(long idLecture) {
        this.idLecture = idLecture;
    }

    public String getNameOfLecture() {
        return nameOfLecture;
    }

    public void setNameOfLecture(String nameOfLecture) {
        this.nameOfLecture = nameOfLecture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdditionalLiterature() {
        return additionalLiterature;
    }

    public void setAdditionalLiterature(String additionalLiterature) {
        this.additionalLiterature = additionalLiterature;
    }

    public Block getBlockID() {
        return blockID;
    }

    public void setBlockID(Block blockID) {
        this.blockID = blockID;
    }

    public Collection<Test> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Test> tenants) {
        this.tenants = tenants;
    }

    public Collection<Photo> getTenants2() {
        return tenants2;
    }

    public void setTenants2(Collection<Photo> tenants2) {
        this.tenants2 = tenants2;
    }
}
