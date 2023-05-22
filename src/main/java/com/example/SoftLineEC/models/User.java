package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;
import java.util.Set;
/**
 * Класс-сущность для работы с пользователями.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size (max = 20, message = "Фамилия не должна привышать 20 символов")
    private String userSur;
    @Size (max = 20, message = "Имя не должно привышать 20 символов")
    private String userNamee;
    @Size (max = 20, message = "Отчество не должно привышать 20 символов")
    private String userPatr;
    @Email(message = "Введите корректный адрес")
    private String username;
    @ValidPassword
    private String password;
    private String repeatPassword;
    private boolean active;
    private String photoLink;
    private String phoneNumber;
    private String edInstitution;
    private Date dateOfBirth;
    @JsonManagedReference
    @OneToMany(mappedBy = "userID", fetch = FetchType.LAZY)
    private Collection<Course> tenants;
    @JsonManagedReference
    @OneToMany(mappedBy = "userID", fetch = FetchType.LAZY)
    private Collection<Address> tenants2;

    @JsonManagedReference
    @OneToMany(mappedBy = "userID", fetch = FetchType.LAZY)
    private Collection<UsersCourses> tenants3;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name="user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    /**
     * Пустой конструктор класса.
     */
    public User() {
    }
    /**
     * Конструктор класса User для создания новых пользователей.
     * @param userSur фамилия пользователя
     * @param userNamee имя пользователя
     * @param userPatr отчество пользователя
     * @param username адрес электронной почты пользователя
     * @param password пароль пользователя
     * @param repeatPassword повторный ввод пароля пользователя
     * @param active флаг активности пользователя
     * @param roles роли, присвоенные пользователю
     * @param photoLink ссылка на фотографию пользователя
     * @param phoneNumber номер телефона пользователя
     * @param edInstitution учебное заведение пользователя
     * @param dateOfBirth дата рождения пользователя
     */
    public User(String userSur, String userNamee, String userPatr, String username, String password, String repeatPassword, boolean active, Set<Role> roles, String photoLink, String phoneNumber, String edInstitution, Date dateOfBirth) {
        this.userSur = userSur;
        this.userNamee = userNamee;
        this.userPatr = userPatr;
        this.username = username;
        this.password = password;
        this.repeatPassword=repeatPassword;
        this.active = active;
        this.roles = roles;
        this.photoLink = photoLink;
        this.phoneNumber = phoneNumber;
        this.edInstitution = edInstitution;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserSur() {
        return userSur;
    }

    public void setUserSur(String userSur) {
        this.userSur = userSur;
    }

    public String getUserNamee() {
        return userNamee;
    }

    public void setUserNamee(String userNamee) {
        this.userNamee = userNamee;
    }

    public String getUserPatr() {
        return userPatr;
    }

    public void setUserPatr(String userPatr) {
        this.userPatr = userPatr;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEdInstitution() {
        return edInstitution;
    }

    public void setEdInstitution(String edInstitution) {
        this.edInstitution = edInstitution;
    }

    public Collection<Course> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Course> tenants) {
        this.tenants = tenants;
    }

    public Collection<Address> getTenants2() {
        return tenants2;
    }

    public void setTenants2(Collection<Address> tenants2) {
        this.tenants2 = tenants2;
    }

    public Collection<UsersCourses> getTenants3() {
        return tenants3;
    }

    public void setTenants3(Collection<UsersCourses> tenants3) {
        this.tenants3 = tenants3;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
