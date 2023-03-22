package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.CourseType;
import com.example.SoftLineEC.models.FormOfEducation;
import com.example.SoftLineEC.repositories.FormOfEducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String FormOfEducationAdd(@ModelAttribute("FormOfEducation") FormOfEducation formOfEducation)
    {
        return "FormOfEducationAdd";
    }

    @PostMapping("/FormOfEducationAdd")
    public String FormOfEducationAddAdd(@ModelAttribute("FormOfEducation") @Valid FormOfEducation formOfEducation, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "FormOfEducationAdd";
        }
        formOfEducationRepository.save(formOfEducation);
        return "redirect:/FormOfEducation";
    }

    @GetMapping("/FormOfEducation/{idFormOfEducation}/edit")
    public String FormOfEducationEdit(@PathVariable("idFormOfEducation") long idFormOfEducation, Model model)
    {
        if(!formOfEducationRepository.existsById(idFormOfEducation)){
            return "redirect:/FormOfEducation";
        }
        FormOfEducation res = formOfEducationRepository.findById(idFormOfEducation).orElseThrow();
        model.addAttribute("FormOfEducation", res);
        return "FormOfEducationEdit";
    }
    @PostMapping("/FormOfEducation/{idFormOfEducation}/edit")
    public String FormOfEducationUpdate(@PathVariable("idFormOfEducation")long idFormOfEducation, @Valid FormOfEducation formOfEducation, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "FormOfEducationEdit";
        }
        formOfEducationRepository.save(formOfEducation);
        return "redirect:/FormOfEducation";
    }
    @GetMapping("/FormOfEducation/{idFormOfEducation}/remove")
    public String FormOfEducationRemove(@PathVariable("idFormOfEducation") long idFormOfEducation, Model model)
    {
        FormOfEducation formOfEducation = formOfEducationRepository.findById(idFormOfEducation).orElseThrow();
        formOfEducationRepository.delete(formOfEducation);
        return "redirect:/FormOfEducation";
    }
}
