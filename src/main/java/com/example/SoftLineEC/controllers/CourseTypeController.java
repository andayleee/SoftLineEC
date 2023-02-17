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
    public String CourseTypeUpdate(@PathVariable("id")long id, @Valid CourseType courseType, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "CourseTypeEdit";
        }
        courseTypeRepository.save(courseType);
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
