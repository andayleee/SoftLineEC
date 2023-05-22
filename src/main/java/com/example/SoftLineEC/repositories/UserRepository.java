package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.User;
import org.springframework.data.repository.CrudRepository;
/**
 * Интерфейс для работы с таблицей пользователей.
 */
public interface UserRepository extends CrudRepository<User,Long> {
    /**
     * Метод для поиска пользователя по имени пользователя.
     * @param username имя пользователя
     * @return пользователь с указанным именем пользователя
     */
    User findUserByUsername(String username);
    /**
     * Метод для поиска пользователя по идентификатору.
     * @param IdUser идентификатор пользователя
     * @return пользователь с указанным идентификатором
     */
    User findUserByid(long IdUser);
}
