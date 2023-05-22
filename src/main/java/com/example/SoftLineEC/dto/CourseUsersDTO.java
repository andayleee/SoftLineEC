package com.example.SoftLineEC.dto;

import lombok.Data;
/**
 * Класс, представляющий объект для передачи информации о количестве пользователей, зарегистрированных на курсе,
 * и количестве пользователей, успешно завершивших курс.
 */
@Data
public class CourseUsersDTO {
    private String courseName;
    private int usersCount;
    private int completeCount;
    /**
     * Конструктор класса.
     * @param courseName название курса
     * @param usersCount количество пользователей на курсе
     * @param completeCount количество пользователей, успешно завершивших курс
     */
    public CourseUsersDTO(String courseName, int usersCount, int completeCount) {
        this.courseName = courseName;
        this.usersCount = usersCount;
        this.completeCount = completeCount;
    }
    /**
     * Пустой конструктор класса.
     */
    public CourseUsersDTO() {}
    /**
     * Метод получения значения поля courseName.
     * @return название курса
     */
    public String getCourseName() {
        return courseName;
    }
    /**
     * Метод установки значения поля courseName.
     * @param courseName название курса
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    /**
     * Метод получения значения поля usersCount.
     * @return количество пользователей на курсе
     */
    public int getUsersCount() {
        return usersCount;
    }
    /**
     * Метод установки значения поля usersCount.
     * @param usersCount количество пользователей на курсе
     */
    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }
    /**
     * Метод получения значения поля completeCount.
     * @return количество пользователей, успешно завершивших курс
     */
    public int getCompleteCount() {
        return completeCount;
    }
    /**
     * Метод установки значения поля completeCount.
     * @param completeCount количество пользователей, успешно завершивших курс
     */
    public void setCompleteCount(int completeCount) {
        this.completeCount = completeCount;
    }
}
