package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.CourseType;
import org.springframework.data.repository.CrudRepository;
/**
 * Интерфейс для работы с таблицей типов курсов.
 */
public interface CourseTypeRepository extends CrudRepository<CourseType, Long> {
    /**
     * Метод для поиска типа курса по названию.
     * @param nameOfCourseType название типа курса
     * @return тип курса с указанным названием
     */
    CourseType findByNameOfCourseType(String nameOfCourseType);
}
