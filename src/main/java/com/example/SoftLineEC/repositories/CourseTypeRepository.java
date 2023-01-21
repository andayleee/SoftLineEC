package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.CourseType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseTypeRepository extends CrudRepository<CourseType, Long> {
    CourseType findByNameOfCourseType(String nameOfCourseType);
}
