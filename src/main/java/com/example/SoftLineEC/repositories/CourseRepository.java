package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.UsersCourses;
import com.example.SoftLineEC.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course,Long> {
    Course findByCategoriesOfStudentsOrNameOfCourse (String categoriesOfStudents, String nameOfCourse);
    @Query("SELECT m FROM Course m WHERE m.categoriesOfStudents LIKE %?1% or m.nameOfCourse LIKE %?1%")
    List<Course> findByCategoriesOfStudentsss (String categoriesOfStudents);
    Course findCoursesByNameOfCourse(String NameOfCourse);
    Iterable<Course> findCoursesByUserID (User userID);
    Course findCourseByIdCourse (Long idCourse);
    @Query("SELECT c FROM Course c JOIN c.tenants2 uc WHERE uc.userID.id = :userId")
    List<Course> findAllByUserId(@Param("userId") Long userId);

}
