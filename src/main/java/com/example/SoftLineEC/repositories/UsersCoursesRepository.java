package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.User;
import com.example.SoftLineEC.models.UsersCourses;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsersCoursesRepository extends CrudRepository<UsersCourses,Long> {
    List<UsersCourses> findUsersCoursesByUserID (User userID);
    List<UsersCourses> findUsersCoursesByCourseID (Course courseID);

    UsersCourses findUsersCoursesByUserIDAndCourseID (User userID, Course courseID);
}
