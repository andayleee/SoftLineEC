package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.Lecture;
import com.example.SoftLineEC.repositories.BlockRepository;
import com.example.SoftLineEC.repositories.CourseRepository;
import com.example.SoftLineEC.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class MainPageController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @GetMapping("/main")
    public String mainPage() {return "MainPage";}
    @RequestMapping(value = "/check-courses", method = RequestMethod.POST)
    @ResponseBody
    public List<Course> checkCourses() {
        Iterable<Course> course = courseRepository.findAll();
        List<Course> courses = new ArrayList<>();
        Iterator<Course> iterator = course.iterator();
        while (iterator.hasNext()) {
            courses.add(iterator.next());
        }
        return courses ;
    }

    @GetMapping("/main/Courses-{idCourse}")
    public String BlockUpdate(@PathVariable("idCourse")long idCourse, Model model, HttpSession session)
    {
        session.setAttribute("idCourse", idCourse);
        Optional<Course> course = courseRepository.findById(idCourse);
        ArrayList<Course> res = new ArrayList<>();
        course.ifPresent(res::add);
        model.addAttribute("Course", res);
        return "MainPageCourseView";
    }
    @RequestMapping(value = "/check-blocks-main", method = RequestMethod.POST)
    @ResponseBody
    public List<Block> checkBlocks(HttpSession session) {
        Long idCourse = (Long) session.getAttribute("idCourse");
        Optional<Course> course = courseRepository.findById(idCourse);
        List<Block> blocks = blockRepository.findBlocksByCourseID(course.get()) ; // blockRepository - экземпляр репозитория, отвечающего за блоки в базе данных

        return blocks ;
    }
    @RequestMapping(value = "/check-lectures-main/{id}", method = RequestMethod.POST)
    @ResponseBody
    public List<Lecture> checkLectures(@PathVariable("id") Long idBlock) {
        Optional<Block> block = blockRepository.findById(idBlock);
        List<Lecture> lectures = lectureRepository.findLecturesByBlockID(block.get()); // blockRepository - экземпляр репозитория, отвечающего за блоки в базе данных

        return lectures ;
    }
    @RequestMapping(value = "/check-coursesType-{id}", method = RequestMethod.POST)
    @ResponseBody
    public String checkCoursesType(@PathVariable("id") Long idCourse) {
        Optional<Course> course = courseRepository.findById(idCourse);
        String courseType = course.get().getCourseTypeID().getNameOfCourseType();
        return courseType;
    }

    @RequestMapping(value = "/check-formOf-{id}", method = RequestMethod.POST)
    @ResponseBody
    public String checkFormOfEducation(@PathVariable("id") Long idCourse) {
        Optional<Course> course = courseRepository.findById(idCourse);
        String formOfEducation = course.get().getFormOfEducationID().getTypeOfEducation();
        return formOfEducation;
    }
}
