package com.example.SoftLineEC.models;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idLecture;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 255,message = "Значение не может быть меньше 1 и больше 255 символов")
    private String nameOfLecture;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 5000,message = "Значение не может быть меньше 1 и больше 5000 символов")
    private String description;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 5000,message = "Значение не может быть меньше 1 и больше 5000 символов")
    private String content;
    @NotBlank(message = "Значение не может быть пустым")
    @Size(min = 1,max = 2000,message = "Значение не может быть меньше 1 и больше 2000 символов")
    private String additionalLiterature;
    @ManyToOne(optional = true)
    private Block blockID;
    @OneToMany(mappedBy = "lectureID", fetch = FetchType.EAGER)
    private Collection<Test> tenants;

    public Lecture(String nameOfLecture, String description, String content, String additionalLiterature, Block blockID) {
        this.nameOfLecture = nameOfLecture;
        this.description = description;
        this.content = content;
        this.additionalLiterature = additionalLiterature;
        this.blockID = blockID;
    }
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
}
