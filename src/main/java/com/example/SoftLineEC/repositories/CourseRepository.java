package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course,Long> {
    Course findByCategoriesOfStudentsOrNameOfCourse (String categoriesOfStudents, String nameOfCourse);
}
