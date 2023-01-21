package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.CourseType;
import com.example.SoftLineEC.repositories.CourseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CourseTypeController {
    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @GetMapping("/CourseType")
    public String CourseType(Model model)
    {
        Iterable<CourseType> courseType = courseTypeRepository.findAll();
        model.addAttribute("CourseType", courseType);
        return "CourseTypeMain";
    }
    @GetMapping("/CourseTypeAdd")
    public String CourseTypeAdd(Model model)
    {
        return "CourseTypeAdd";
    }

    @PostMapping("/CourseTypeAdd")
    public String CourseTypeAddAdd(@RequestParam String nameOfCourseType,
                                   Model model)
    {
        CourseType сourseType = new CourseType(nameOfCourseType);
        courseTypeRepository.save(сourseType);
        return "redirect:/CourseType";
    }
    @GetMapping("/CourseType/{id}/edit")
    public String CourseTypeEdit(@PathVariable("id") long id, Model model)
    {
        if(!courseTypeRepository.existsById(id)){
            return "redirect:/CourseType";
        }
        CourseType res = courseTypeRepository.findById(id).orElseThrow();
        model.addAttribute("CourseType", res);
        return "CourseTypeEdit";
    }
    @PostMapping("/CourseType/{id}/edit")
    public String CourseTypeUpdate(@PathVariable("id")long id, @RequestParam String nameOfCourseType,
                                   Model model)
    {
        CourseType res = courseTypeRepository.findById(id).orElseThrow();
        res.setNameOfCourseType(nameOfCourseType);
        return "redirect:/CourseType";
    }
    @GetMapping("/CourseType/{id}/remove")
    public String CourseTypeRemove(@PathVariable("id") long id, Model model)
    {
        CourseType courseType = courseTypeRepository.findById(id).orElseThrow();
        courseTypeRepository.delete(courseType);
        return "redirect:/CourseType";
    }
}
