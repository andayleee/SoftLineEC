package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.dto.CourseUsersDTO;
import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.Lecture;
import com.example.SoftLineEC.models.User;
import com.example.SoftLineEC.models.UsersCourses;
import com.example.SoftLineEC.repositories.CourseRepository;
import com.example.SoftLineEC.repositories.UserRepository;
import com.example.SoftLineEC.repositories.UsersCoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
/**
 * Контроллер для работы со статистикой по курсам и пользователям.
 */
@Controller
public class Statistics {
    /**
     * Репозиторий курсов пользователей.
     */
    @Autowired
    private UsersCoursesRepository usersCoursesRepository;
    /**
     * Репозиторий курсов.
     */
    @Autowired
    private CourseRepository courseRepository;
    /**
     * Обрабатывает GET-запрос на получение страницы со статистикой.
     * @return имя представления для страницы со статистикой
     */
    @GetMapping("/Statistics")
    public String StatisticsView()
    {
        return "Statistics";
    }
    /**
     * Обрабатывает POST-запрос на получение данных для построения графика на странице со статистикой.
     * @param session объект HttpSession, используемый для получения данных о текущем пользователе
     * @return список объектов CourseUsersDTO, содержащих данные о количестве пользователей и завершивших курс для каждого курса
     */
    @RequestMapping(value = "/get-statistics-data", method = RequestMethod.POST)
    @ResponseBody
    public List<CourseUsersDTO> checkLecturesText(HttpSession session) {
        List<CourseUsersDTO> courseUsersList = new ArrayList<>();
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            List<UsersCourses> usersCourses = usersCoursesRepository.findUsersCoursesByCourseID(course);
            int completeCount = 0;
            for (UsersCourses usersCourse : usersCourses) {
                if (usersCourse.isCompleteness()){
                    completeCount++;
                }
             }
            int count = usersCourses.size();
            CourseUsersDTO courseUsersDTO = new CourseUsersDTO(course.getNameOfCourse(), count, completeCount);
            courseUsersList.add(courseUsersDTO);
        }
        return courseUsersList;
    }
}
