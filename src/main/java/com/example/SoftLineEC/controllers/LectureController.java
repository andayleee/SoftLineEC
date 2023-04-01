package com.example.SoftLineEC.controllers;
import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.Lecture;
import com.example.SoftLineEC.models.Photo;
import com.example.SoftLineEC.repositories.BlockRepository;
import com.example.SoftLineEC.repositories.LectureRepository;
import com.example.SoftLineEC.repositories.PhotoRepository;
import com.example.SoftLineEC.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
@Controller
public class LectureController {
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private PhotoRepository photoRepository;

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
                                @RequestParam("file") MultipartFile file, Photo photo,
                                @RequestParam String nameOfBlock, Model addr) throws IOException
    {
        if (bindingResult.hasErrors()) {
            Iterable<Block> nameOfBlockk = blockRepository.findAll();
            addr.addAttribute("nameOfBlock",nameOfBlockk);
            return "LectureAdd";
        }
        Lecture.setBlockID(blockRepository.findByNameOfBlockOrDescriptionOrDuration(nameOfBlock,nameOfBlock,nameOfBlock));
        lectureRepository.save(Lecture);
        FileUploadService.saveFile(file);
        photo.setPhotoPath(FileUploadService.getFilePath2());
        photo.setLectureID(lectureRepository.findByNameOfLecture(Lecture.getNameOfLecture()));
        photoRepository.save(photo);
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
                                @Valid Lecture lecture, BindingResult bindingResult,
                                @RequestParam("file") MultipartFile file, Photo photo,
                                @RequestParam String nameOfBlock) throws IOException {
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
