package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class MainPageController {
    @Autowired
    private CourseRepository courseRepository;
    @GetMapping("/main")
    public String mainPage(Model model) {return "MainPage";}
    @RequestMapping(value = "/check-courses", method = RequestMethod.POST)
    @ResponseBody
    public List<Course> checkCourses(HttpSession session) {
        Iterable<Course> course = courseRepository.findAll();
        List<Course> courses = new ArrayList<>();
        Iterator<Course> iterator = course.iterator();
        while (iterator.hasNext()) {
            courses.add(iterator.next());
        }
        return courses ;
    }
}
