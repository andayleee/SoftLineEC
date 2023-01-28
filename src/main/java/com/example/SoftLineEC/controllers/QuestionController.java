package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Test;
import com.example.SoftLineEC.models.Question;
import com.example.SoftLineEC.repositories.QuestionRepository;
import com.example.SoftLineEC.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
@Controller
public class QuestionController {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/Question")
    public String Question(Model model)
    {
        Iterable<Question> question = questionRepository.findAll();
        model.addAttribute("Question", question);
        return "QuestionMain";
    }
    @GetMapping("/QuestionAdd")
    public String QuestionAdd(@ModelAttribute("Question") Question Question, Model addr)
    {
        Iterable<Test> nameOfTest= testRepository.findAll();
        addr.addAttribute("nameOfTest",nameOfTest);
        return "QuestionAdd";
    }
    @PostMapping("/QuestionAdd")
    public String QuestionAddAdd(@ModelAttribute("Question") Question Question,
                             @RequestParam String nameOfTest, Model addr)
    {
        Question.setTestID(testRepository.findByNameOfTest(nameOfTest));
        questionRepository.save(Question);
        return "QuestionMain";
    }
    @GetMapping("/Question/{id}/edit")
    public String QuestionEdit(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Question> question = questionRepository.findById(id);
        ArrayList<Question> res = new ArrayList<>();
        question.ifPresent(res::add);
        model.addAttribute("Question", res);
        Iterable<Test> test = testRepository.findAll();
        model.addAttribute("Test",test);
        if(!questionRepository.existsById(id)){
            return "redirect:/Question";
        }
        return "QuestionEdit";
    }

    @PostMapping("/Question/{id}/edit")
    public String QuestionUpdate(@PathVariable("id")long id,
                                 Question question, @RequestParam String nameOfTest)
    {
        question.setTestID(testRepository.findByNameOfTest(nameOfTest));
        questionRepository.save(question);
        return "redirect:/Question";
    }

    @GetMapping("/Question/{id}/remove")
    public String QuestionRemove(@PathVariable("id") long id, Model model)
    {
        Question question = questionRepository.findById(id).orElseThrow();
        questionRepository.delete(question);
        return "redirect:/Question";
    }
}
