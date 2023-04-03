package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.Lecture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course,Long> {
    Course findByCategoriesOfStudentsOrNameOfCourse (String categoriesOfStudents, String nameOfCourse);
    @Query("SELECT m FROM Course m WHERE m.categoriesOfStudents LIKE %?1% or m.nameOfCourse LIKE %?1%")
    List<Course> findByCategoriesOfStudentsss (String categoriesOfStudents);

}
