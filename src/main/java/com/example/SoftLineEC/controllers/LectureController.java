package com.example.SoftLineEC.controllers;
import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.Lecture;
import com.example.SoftLineEC.repositories.BlockRepository;
import com.example.SoftLineEC.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
@Controller
public class LectureController {
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private LectureRepository lectureRepository;

    @GetMapping("/Lecture")
    public String Lecture(Model model)
    {
        Iterable<Lecture> lecture = lectureRepository.findAll();
        model.addAttribute("Lecture", lecture);
        return "LectureMain";
    }

    @GetMapping("/LectureAdd")
    public String LectureAdd(@ModelAttribute("Lecture") Lecture Lecture, Model addr)
    {
        Iterable<Block> nameOfBlock = blockRepository.findAll();
        addr.addAttribute("nameOfBlock",nameOfBlock);
        return "LectureAdd";
    }
    @PostMapping("/LectureAdd")
    public String LectureAddAdd(@ModelAttribute("Lecture") Lecture Lecture,
                              @RequestParam String nameOfBlock, Model addr)
    {
        Lecture.setBlockID(blockRepository.findByNameOfBlockOrDescriptionOrDuration(nameOfBlock,nameOfBlock,nameOfBlock));
        lectureRepository.save(Lecture);
        return "LectureMain";
    }
    @GetMapping("/Lecture/{id}/edit")
    public String LectureEdit(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Lecture> lecture = lectureRepository.findById(id);
        ArrayList<Lecture> res = new ArrayList<>();
        lecture.ifPresent(res::add);
        model.addAttribute("Lecture", res);
        Iterable<Block> block = blockRepository.findAll();
        model.addAttribute("Block",block);
        if(!lectureRepository.existsById(id)){
            return "redirect:/Lecture";
        }
        return "LectureEdit";
    }

    @PostMapping("/Lecture/{id}/edit")
    public String LectureUpdate(@PathVariable("id")long id,
                                Lecture lecture, @RequestParam String nameOfBlock)
    {
        lecture.setBlockID(blockRepository.findByNameOfBlockOrDescriptionOrDuration(nameOfBlock,nameOfBlock,nameOfBlock));
        lectureRepository.save(lecture);
        return "redirect:/Lecture";
    }

    @GetMapping("/Lecture/{id}/remove")
    public String LectureRemove(@PathVariable("id") long id, Model model)
    {
        Lecture lecture = lectureRepository.findById(id).orElseThrow();
        lectureRepository.delete(lecture);
        return "redirect:/Lecture";
    }
}
