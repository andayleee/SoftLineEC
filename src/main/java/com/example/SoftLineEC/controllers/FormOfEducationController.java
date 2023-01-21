package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.CourseType;
import com.example.SoftLineEC.models.FormOfEducation;
import com.example.SoftLineEC.repositories.FormOfEducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormOfEducationController {
    @Autowired
    private FormOfEducationRepository formOfEducationRepository;

    @GetMapping("/FormOfEducation")
    public String FormOfEducation(Model model)
    {
        Iterable<FormOfEducation> formOfEducation = formOfEducationRepository.findAll();
        model.addAttribute("FormOfEducation", formOfEducation);
        return "FormOfEducationMain";
    }

    @GetMapping("/FormOfEducationAdd")
    public String FormOfEducationAdd(Model model)
    {
        return "FormOfEducationAdd";
    }

    @PostMapping("/FormOfEducationAdd")
    public String FormOfEducationAddAdd(@RequestParam String typeOfEducation,
                                   Model model)
    {
        FormOfEducation formOfEducation = new FormOfEducation(typeOfEducation);
        formOfEducationRepository.save(formOfEducation);
        return "redirect:/FormOfEducation";
    }

    @GetMapping("/FormOfEducation/{id}/edit")
    public String FormOfEducationEdit(@PathVariable("id") long id, Model model)
    {
        if(!formOfEducationRepository.existsById(id)){
            return "redirect:/FormOfEducation";
        }
        FormOfEducation res = formOfEducationRepository.findById(id).orElseThrow();
        model.addAttribute("FormOfEducation", res);
        return "FormOfEducationEdit";
    }
    @PostMapping("/FormOfEducation/{id}/edit")
    public String FormOfEducationUpdate(@PathVariable("id")long id, @RequestParam String typeOfEducation,
                                   Model model)
    {
        FormOfEducation res = formOfEducationRepository.findById(id).orElseThrow();
        res.setTypeOfEducation(typeOfEducation);
        return "redirect:/FormOfEducation";
    }
    @GetMapping("/FormOfEducation/{id}/remove")
    public String FormOfEducationRemove(@PathVariable("id") long id, Model model)
    {
        FormOfEducation formOfEducation = formOfEducationRepository.findById(id).orElseThrow();
        formOfEducationRepository.delete(formOfEducation);
        return "redirect:/FormOfEducation";
    }
}
