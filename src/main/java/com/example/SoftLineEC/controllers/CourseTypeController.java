package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.CourseType;
import com.example.SoftLineEC.repositories.CourseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String CourseTypeAdd(@ModelAttribute ("CourseType") CourseType courseType)
    {
        return "CourseTypeAdd";
    }

    @PostMapping("/CourseTypeAdd")
    public String CourseTypeAddAdd(@ModelAttribute ("CourseType") @Valid CourseType courseType, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "CourseTypeAdd";
        }
        courseTypeRepository.save(courseType);
        return "redirect:/CourseType";
    }
    @GetMapping("/CourseType/{idCourseType}/edit")
    public String CourseTypeEdit(@PathVariable("idCourseType") long idCourseType, Model model)
    {
        if(!courseTypeRepository.existsById(idCourseType)){
            return "redirect:/CourseType";
        }
        CourseType res = courseTypeRepository.findById(idCourseType).orElseThrow();
        model.addAttribute("CourseType", res);
        return "CourseTypeEdit";
    }
    @PostMapping("/CourseType/{idCourseType}/edit")
    public String CourseTypeUpdate(@ModelAttribute("courseType") @Valid CourseType courseType, BindingResult bindingResult, @PathVariable("idCourseType")long idCourseType)
    {
        if (bindingResult.hasErrors())
        {
            return "CourseTypeEdit";
        }
        courseTypeRepository.save(courseType);
        return "redirect:/CourseType";
    }
    @GetMapping("/CourseType/{idCourseType}/remove")
    public String CourseTypeRemove(@PathVariable("idCourseType") long idCourseType, Model model)
    {
        CourseType courseType = courseTypeRepository.findById(idCourseType).orElseThrow();
        courseTypeRepository.delete(courseType);
        return "redirect:/CourseType";
    }
}
