package com.example.SoftLineEC.models;

import org.springframework.security.core.GrantedAuthority;
/**
 * Перечисление для определения ролей пользователей.
 * Реализует интерфейс GrantedAuthority.
 */
public enum Role implements GrantedAuthority {
    USER, ADMIN, EMPLOYEE;
    /**
     * Метод получения значения поля authority для работы с Spring Security.
     * @return название роли
     */
    @Override
    public String getAuthority() {
        return name();
    }
}
