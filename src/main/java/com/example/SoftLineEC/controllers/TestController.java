package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Test;
import com.example.SoftLineEC.models.Lecture;
import com.example.SoftLineEC.repositories.LectureRepository;
import com.example.SoftLineEC.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;
@Controller
public class TestController {
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private TestRepository testRepository;

    @GetMapping("/Test")
    public String Test(Model model)
    {
        Iterable<Test> test = testRepository.findAll();
        model.addAttribute("Test", test);
        return "TestMain";
    }
    @GetMapping("/TestAdd")
    public String TestAdd(@ModelAttribute("Test") Test Test, Model addr)
    {
        Iterable<Lecture> nameOfLecture = lectureRepository.findAll();
        addr.addAttribute("nameOfLecture",nameOfLecture);
        return "TestAdd";
    }
    @PostMapping("/TestAdd")
    public String TestAddAdd(@ModelAttribute("Test") @Valid Test Test, BindingResult bindingResult,
                                @RequestParam String nameOfLecture, Model addr)
    {
        if (bindingResult.hasErrors()) {
            Iterable<Test> test = testRepository.findAll();
            addr.addAttribute("Test", test);
            return "TestAdd";
        }
        Test.setLectureID(lectureRepository.findByNameOfLecture(nameOfLecture));
        testRepository.save(Test);
        return "TestMain";
    }
    @GetMapping("/Test/{id}/edit")
    public String TestEdit(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Test> test = testRepository.findById(id);
        ArrayList<Test> res = new ArrayList<>();
        test.ifPresent(res::add);
        model.addAttribute("Test", res);
        Iterable<Lecture> lecture = lectureRepository.findAll();
        model.addAttribute("Lecture",lecture);
        if(!testRepository.existsById(id)){
            return "redirect:/Test";
        }
        return "TestEdit";
    }

    @PostMapping("/Test/{id}/edit")
    public String TestUpdate(@PathVariable("id")long id,
                             @Valid Test test, BindingResult bindingResult, @RequestParam String nameOfLecture)
    {
        if (bindingResult.hasErrors())
            return "TestEdit";
        test.setLectureID(lectureRepository.findByNameOfLecture(nameOfLecture));
        testRepository.save(test);
        return "redirect:/Test";
    }

    @GetMapping("/Test/{id}/remove")
    public String TestRemove(@PathVariable("id") long id, Model model)
    {
        Test test = testRepository.findById(id).orElseThrow();
        testRepository.delete(test);
        return "redirect:/Test";
    }
}
