package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.AnswerOptions;
import com.example.SoftLineEC.models.Question;
import com.example.SoftLineEC.models.Test;
import com.example.SoftLineEC.repositories.AnswerOptionsRepository;
import com.example.SoftLineEC.repositories.QuestionRepository;
import com.example.SoftLineEC.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
@Controller
public class AnswerOptionsController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerOptionsRepository answerOptionsRepository;

    @GetMapping("/AnswerOptions")
    public String AnswerOptions(Model model)
    {
        Iterable<AnswerOptions> answerOptions = answerOptionsRepository.findAll();
        model.addAttribute("AnswerOptions", answerOptions);
        return "AnswerOptionsMain";
    }
    @GetMapping("/AnswerOptionsAdd")
    public String AnswerOptionsAdd(@ModelAttribute("AnswerOptions") AnswerOptions AnswerOptions, Model addr)
    {
        Iterable<Question> nameOfQuestion= questionRepository.findAll();
        addr.addAttribute("nameOfQuestion",nameOfQuestion);
        return "AnswerOptionsAdd";
    }
    @PostMapping("/AnswerOptionsAdd")
    public String AnswerOptionsAddAdd(@ModelAttribute("AnswerOptions") AnswerOptions AnswerOptions,
                                 @RequestParam String nameOfQuestion, Model addr)
    {
        AnswerOptions.setQuestionID(questionRepository.findByNameOfQuestion(nameOfQuestion));
        answerOptionsRepository.save(AnswerOptions);
        return "AnswerOptionsMain";
    }

    @GetMapping("/AnswerOptions/{id}/edit")
    public String AnswerOptionsEdit(@PathVariable(value = "id") long id, Model model)
    {
        Optional<AnswerOptions> answerOptions = answerOptionsRepository.findById(id);
        ArrayList<AnswerOptions> res = new ArrayList<>();
        answerOptions.ifPresent(res::add);
        model.addAttribute("AnswerOptions", res);
        Iterable<Question> question = questionRepository.findAll();
        model.addAttribute("Question",question);
        if(!answerOptionsRepository.existsById(id)){
            return "redirect:/AnswerOptions";
        }
        return "AnswerOptionsEdit";
    }

    @PostMapping("/AnswerOptions/{id}/edit")
    public String AnswerOptionsUpdate(@PathVariable("id")long id,
                                      AnswerOptions answerOptions, @RequestParam String nameOfQuestion)
    {
        answerOptions.setQuestionID(questionRepository.findByNameOfQuestion(nameOfQuestion));
        answerOptionsRepository.save(answerOptions);
        return "redirect:/AnswerOptions";
    }

    @GetMapping("/AnswerOptions/{id}/remove")
    public String AnswerOptionsRemove(@PathVariable("id") long id, Model model)
    {
        AnswerOptions answerOptions = answerOptionsRepository.findById(id).orElseThrow();
        answerOptionsRepository.delete(answerOptions);
        return "redirect:/AnswerOptions";
    }
}
