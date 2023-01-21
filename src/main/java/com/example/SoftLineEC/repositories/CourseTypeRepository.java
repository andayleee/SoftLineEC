package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.CourseType;
import org.springframework.data.repository.CrudRepository;

public interface CourseTypeRepository extends CrudRepository<CourseType, Long> {
    CourseType findByNameOfCourseType(String nameOfCourseType);
}
