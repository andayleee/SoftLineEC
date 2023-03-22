package com.example.SoftLineEC.controllers;
import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.Lecture;
import com.example.SoftLineEC.repositories.BlockRepository;
import com.example.SoftLineEC.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String LectureAddAdd(@ModelAttribute("Lecture") @Valid Lecture Lecture, BindingResult bindingResult,
                              @RequestParam String nameOfBlock, Model addr)
    {
        if (bindingResult.hasErrors()) {
            Iterable<Block> nameOfBlockk = blockRepository.findAll();
            addr.addAttribute("nameOfBlock",nameOfBlockk);
            return "LectureAdd";
        }
        Lecture.setBlockID(blockRepository.findByNameOfBlockOrDescriptionOrDuration(nameOfBlock,nameOfBlock,nameOfBlock));
        lectureRepository.save(Lecture);
        return "redirect:/Lecture";
    }
    @GetMapping("/Lecture/{idLecture}/edit")
    public String LectureEdit(@PathVariable(value = "idLecture") long idLecture, Model model)
    {
        if(!lectureRepository.existsById(idLecture)){
            return "redirect:/Lecture";
        }
        Optional<Lecture> lecture = lectureRepository.findById(idLecture);
        ArrayList<Lecture> res = new ArrayList<>();
        lecture.ifPresent(res::add);
        model.addAttribute("Lecture", res);
        Iterable<Block> block = blockRepository.findAll();
        model.addAttribute("Block",block);
        return "LectureEdit";
    }

    @PostMapping("/Lecture/{idLecture}/edit")
    public String LectureUpdate(@PathVariable("idLecture")long idLecture,
                                @Valid Lecture lecture, BindingResult bindingResult, @RequestParam String nameOfBlock)
    {
        if (bindingResult.hasErrors())
            return "LectureEdit";
        lecture.setBlockID(blockRepository.findByNameOfBlockOrDescriptionOrDuration(nameOfBlock,nameOfBlock,nameOfBlock));
        lectureRepository.save(lecture);
        return "redirect:/Lecture";
    }

    @GetMapping("/Lecture/{idLecture}/remove")
    public String LectureRemove(@PathVariable("idLecture") long idLecture, Model model)
    {
        Lecture lecture = lectureRepository.findById(idLecture).orElseThrow();
        lectureRepository.delete(lecture);
        return "redirect:/Lecture";
    }
}
