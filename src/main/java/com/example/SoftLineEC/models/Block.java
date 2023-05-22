package com.example.SoftLineEC.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
/**
 * Класс-сущность для работы с блоками курсов.
 */
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
    /**
     * Конструктор класса.
     * @param nameOfBlock название блока
     * @param description описание блока
     * @param duration длительность блока
     * @param courseID курс, к которому относится блок
     */
    public Block(String nameOfBlock, String description, String duration, Course courseID) {
        this.nameOfBlock = nameOfBlock;
        this.description = description;
        this.duration = duration;
        this.courseID = courseID;
    }
    /**
     * Пустой конструктор класса.
     */
    public Block (){}
    /**
     * Метод получения значения поля idBlock.
     * @return идентификатор блока
     */
    public long getIdBlock() {
        return idBlock;
    }
    /**
     * Метод установки значения поля idBlock.
     * @param idBlock идентификатор блока
     */
    public void setIdBlock(long idBlock) {
        this.idBlock = idBlock;
    }
    /**
     * Метод получения значения поля nameOfBlock.
     * @return название блока
     */
    public String getNameOfBlock() {
        return nameOfBlock;
    }
    /**
     * Метод установки значения поля nameOfBlock.
     * @param nameOfBlock название блока
     */
    public void setNameOfBlock(String nameOfBlock) {
        this.nameOfBlock = nameOfBlock;
    }
    /**
     * Метод получения значения поля description.
     * @return описание блока
     */
    public String getDescription() {
        return description;
    }
    /**
     * Метод установки значения поля description.
     * @param description описание блока
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Метод получения значения поля duration.
     * @return длительность блока
     */
    public String getDuration() {
        return duration;
    }
    /**
     * Метод установки значения поля duration.
     * @param duration длительность блока
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }
    /**
     * Метод получения значения поля courseID.
     * @return курс, к которому относится блок
     */
    public Course getCourseID() {
        return courseID;
    }
    /**
     * Метод установки значения поля courseID.
     * @param courseID курс, к которому относится блок
     */
    public void setCourseID(Course courseID) {
        this.courseID = courseID;
    }
    /**
     * Метод получения значения поля tenants.
     * @return лекции, которые относятся к данному блоку
     */
    public Collection<Lecture> getTenants() {
        return tenants;
    }
    /**
     * Метод установки значения поля tenants.
     * @param tenants лекции, которые относятся к данному блоку
     */
    public void setTenants(Collection<Lecture> tenants) {
        this.tenants = tenants;
    }
}
