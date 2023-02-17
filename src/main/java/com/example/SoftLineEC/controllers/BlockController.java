package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.repositories.BlockRepository;
import com.example.SoftLineEC.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlockController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private BlockRepository blockRepository;

    @GetMapping("/Block")
    public String Block(Model model)
    {
        Iterable<Block> block = blockRepository.findAll();
        model.addAttribute("Block", block);
        return "BlockMain";
    }

    @GetMapping("/BlockAdd")
    public String BlockAdd(@ModelAttribute("Block") Block Block, Model addr)
    {
        Iterable<Course> nameOfCourse = courseRepository.findAll();
        addr.addAttribute("nameOfCourse",nameOfCourse);
        return "BlockAdd";
    }

    @PostMapping("/BlockAdd")
    public String BlockAddAdd(@ModelAttribute("Block") @Valid Block Block, BindingResult bindingResult,
                               @RequestParam String nameOfCourse, Model addr)
    {
        if (bindingResult.hasErrors()) {
            Iterable<Course> Course = courseRepository.findAll();
            addr.addAttribute("nameOfCourse",Course);
            return "BlockAdd";
        }
        Block.setCourseID(courseRepository.findByCategoriesOfStudentsOrNameOfCourse(nameOfCourse,nameOfCourse));
        blockRepository.save(Block);
        return "BlockMain";
    }

    @GetMapping("/Block/{id}/edit")
    public String BlockEdit(@PathVariable(value = "id") long id, Model model)
    {
        if(!blockRepository.existsById(id)){
            return "redirect:/Block";
        }
        Optional<Block> block = blockRepository.findById(id);
        ArrayList<Block> res = new ArrayList<>();
        block.ifPresent(res::add);
        model.addAttribute("Block", res);
        Iterable<Course> course = courseRepository.findAll();
        model.addAttribute("Course",course);
        return "BlockEdit";
    }

    @PostMapping("/Block/{id}/edit")
    public String BlockUpdate(@PathVariable("id")long id,
                              @Valid Block block, BindingResult bindingResult, @RequestParam String nameOfCourse)
    {
        if (bindingResult.hasErrors())
            return "BlockEdit";
        block.setCourseID(courseRepository.findByCategoriesOfStudentsOrNameOfCourse(nameOfCourse,nameOfCourse));
        blockRepository.save(block);
        return "redirect:/Block";
    }

    @GetMapping("/Block/{id}/remove")
    public String BlockRemove(@PathVariable("id") long id, Model model)
    {
        Block block = blockRepository.findById(id).orElseThrow();
        blockRepository.delete(block);
        return "redirect:/Block";
    }
}
