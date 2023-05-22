package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.User;
import com.example.SoftLineEC.models.UsersCourses;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
/**
 * Интерфейс для работы со связующей таблицей пользователей и курсов.
 */
public interface UsersCoursesRepository extends CrudRepository<UsersCourses,Long> {
    /**
     * Метод для поиска записей об участии пользователей в курсах по идентификатору пользователя.
     * @param userID идентификатор пользователя
     * @return список записей об участии данного пользователя в курсах
     */
    List<UsersCourses> findUsersCoursesByUserID (User userID);
    /**
     * Метод для поиска записей об участии пользователей в курсах по идентификатору курса.
     * @param courseID идентификатор курса
     * @return список записей об участии пользователей в данном курсе
     */
    List<UsersCourses> findUsersCoursesByCourseID (Course courseID);
    /**
     * Метод для поиска записи об участии конкретного пользователя в конкретном курсе.
     * @param userID идентификатор пользователя
     * @param courseID идентификатор курса
     * @return запись об участии данного пользователя в данном курсе, если она есть; null в противном случае
     */
    UsersCourses findUsersCoursesByUserIDAndCourseID (User userID, Course courseID);
}
