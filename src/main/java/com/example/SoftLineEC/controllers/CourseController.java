package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.CourseType;
import com.example.SoftLineEC.models.FormOfEducation;
import com.example.SoftLineEC.repositories.CourseRepository;
import com.example.SoftLineEC.repositories.CourseTypeRepository;
import com.example.SoftLineEC.repositories.FormOfEducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseTypeRepository courseTypeRepository;
    @Autowired
    private FormOfEducationRepository formOfEducationRepository;
    @GetMapping("/Course")
    public String Course(@RequestParam(defaultValue = "") String search, Model model)
    {
        if (search.equals("")) {
            Iterable<Course> course = courseRepository.findAll();
            model.addAttribute("Course", course);
        }
        return "CourseMain";
    }
    @GetMapping("/CourseAdd")
    public String CourseAdd(@ModelAttribute("Course") Course Course, Model addr)
    {
        Iterable<CourseType> courseType = courseTypeRepository.findAll();
        addr.addAttribute("CourseTypes",courseType);
        Iterable<FormOfEducation> formOfEducations = formOfEducationRepository.findAll();
        addr.addAttribute("FormOfEducations",formOfEducations);
        return "CourseAdd";
    }

    @PostMapping("/CourseAdd")
    public String CourseAddAdd(@ModelAttribute("Course") @Valid Course course, BindingResult bindingResult,
                               @RequestParam String nameOfCourseType,@RequestParam String typeOfEducation, Model addr)
    {
        if (bindingResult.hasErrors())
        {
            Iterable<CourseType> courseType = courseTypeRepository.findAll();
            addr.addAttribute("CourseTypes",courseType);
            Iterable<FormOfEducation> formOfEducations = formOfEducationRepository.findAll();
            addr.addAttribute("FormOfEducations",formOfEducations);
            return "CourseAdd";
        }
        course.setCourseTypeID(courseTypeRepository.findByNameOfCourseType(nameOfCourseType));
        course.setFormOfEducationID(formOfEducationRepository.findByTypeOfEducation(typeOfEducation));
        courseRepository.save(course);
        return "CourseMain";
    }

    @GetMapping("/Course/{id}/edit")
    public String CourseEdit(@PathVariable(value = "id") long id, Model model)
    {
        if(!courseRepository.existsById(id)){
            return "redirect:/Course";
        }
        Optional<Course> course = courseRepository.findById(id);
        ArrayList<Course> res = new ArrayList<>();
        course.ifPresent(res::add);
        model.addAttribute("Courses", res);
        Iterable<CourseType> courseType = courseTypeRepository.findAll();
        model.addAttribute("CourseTypes",courseType);
        Iterable<FormOfEducation> formOfEducations = formOfEducationRepository.findAll();
        model.addAttribute("FormOfEducations",formOfEducations);
        return "CourseEdit";
    }

    @PostMapping("/Course/{id}/edit")
    public String CourseUpdate(@PathVariable("id")long id,
                               @Valid Course course, BindingResult bindingResult, @RequestParam String nameOfCourseType,@RequestParam String typeOfEducation)
    {
        if (bindingResult.hasErrors())
            return "CourseEdit";
        course.setCourseTypeID(courseTypeRepository.findByNameOfCourseType(nameOfCourseType));
        course.setFormOfEducationID(formOfEducationRepository.findByTypeOfEducation(typeOfEducation));
        courseRepository.save(course);
        return "redirect:/Course";
    }

    @GetMapping("/Course/{id}/remove")
    public String CourseRemove(@PathVariable("id") long id, Model model)
    {
        Course course = courseRepository.findById(id).orElseThrow();
        courseRepository.delete(course);
        return "redirect:/Course";
    }

    @PostMapping("/CourseSearch")
    public String CourseSearch(@RequestParam(defaultValue = "") String search, Model model)
    {
        List<Course> result1 = courseRepository.findByCategoriesOfStudentsss(search);
        model.addAttribute("Course", result1);
        return Course(search,model);
    }
}
