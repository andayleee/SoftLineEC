package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.UsersCourses;
import com.example.SoftLineEC.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * Интерфейс для работы с таблицей курсов.
 */
public interface CourseRepository extends CrudRepository<Course,Long> {
    /**
     * Метод для поиска курса по категориям студентов или названию курса.
     * @param categoriesOfStudents категории студентов, на которые рассчитан курс
     * @param nameOfCourse название курса
     * @return курс, содержащий указанные данные
     */
    Course findByCategoriesOfStudentsOrNameOfCourse (String categoriesOfStudents, String nameOfCourse);
    /**
     * Метод для поиска курсов по категориям студентов.
     * @param categoriesOfStudents категории студентов, на которые рассчитан курс
     * @return список курсов, содержащих указанные категории студентов
     */
    @Query("SELECT m FROM Course m WHERE m.categoriesOfStudents LIKE %?1% or m.nameOfCourse LIKE %?1%")
    List<Course> findByCategoriesOfStudentsss (String categoriesOfStudents);
    /**
     * Метод для поиска курса по названию.
     * @param NameOfCourse название курса
     * @return курс с указанным названием
     */
    Course findCoursesByNameOfCourse(String NameOfCourse);
    /**
     * Метод для поиска курсов по идентификатору пользователя.
     * @param userID идентификатор пользователя
     * @return список курсов, связанных с данным пользователем
     */
    Iterable<Course> findCoursesByUserID (User userID);
    /**
     * Метод для поиска курса по идентификатору.
     * @param idCourse идентификатор курса
     * @return курс с указанным идентификатором
     */
    Course findCourseByIdCourse (Long idCourse);
    /**
     * Метод для поиска всех курсов, связанных с указанным пользователем.
     * @param userId идентификатор пользователя
     * @return список курсов
     */
    @Query("SELECT c FROM Course c JOIN c.tenants2 uc WHERE uc.userID.id = :userId")
    List<Course> findAllByUserId(@Param("userId") Long userId);
    /**
     * Метод для получения всех курсов.
     * @return список всех курсов
     */
    List<Course> findAll ();

}
