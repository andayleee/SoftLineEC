package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.*;
import com.example.SoftLineEC.repositories.*;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Controller
public class CreateCourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private FormOfEducationRepository formOfEducationRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Value("0")
    private long idCourse;

    LocalDate currentDate = LocalDate.now();

    @GetMapping("/CreateNewCourse")
    public String CreateNewCoursePage(@ModelAttribute("Course") Course Course, Model addr) {
        Iterable<CourseType> courseType = courseTypeRepository.findAll();
        addr.addAttribute("CourseTypes",courseType);
        Iterable<FormOfEducation> formOfEducations = formOfEducationRepository.findAll();
        addr.addAttribute("FormOfEducations",formOfEducations);
        return "CreateNewCourse";
    }

    @PostMapping("/CreateNewCourse")
    public String CoursesAdd(@ModelAttribute("Course") @Valid Course course, BindingResult bindingResult,
                               @RequestParam String nameOfCourseType, @RequestParam String typeOfEducation, Model addr)
    {
        if (bindingResult.hasErrors())
        {
            Iterable<CourseType> courseType = courseTypeRepository.findAll();
            addr.addAttribute("CourseTypes",courseType);
            Iterable<FormOfEducation> formOfEducations = formOfEducationRepository.findAll();
            addr.addAttribute("FormOfEducations",formOfEducations);
            return "CreateNewCourse";
        }
        course.setCourseTypeID(courseTypeRepository.findByNameOfCourseType(nameOfCourseType));
        course.setFormOfEducationID(formOfEducationRepository.findByTypeOfEducation(typeOfEducation));
        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);
        course.setDateOfCreation(date);
        courseRepository.save(course);
        idCourse = course.getIdCourse();
        return "redirect:/CreateNewCourse/details";
    }

    @GetMapping("/CreateNewCourse/details")
    public String CourseEdit(Model model)
    {
        if(!courseRepository.existsById(idCourse)){
            return "redirect:/CreateNewCourse";
        }
        return "CourseDetails";
    }

    @GetMapping("/CreateNewCourse/details/edit")
    public String CourseEditt(Model model)
    {
        if(!courseRepository.existsById(idCourse)){

        }
        return "CourseDetailsEdit";
    }

    @GetMapping("/CourseDescription")
    public String CourseDescriptionPage(Model model) {
        if(!courseRepository.existsById(idCourse)){
            return "redirect:/Course";
        }
        Optional<Course> course = courseRepository.findById(idCourse);
        ArrayList<Course> res = new ArrayList<>();
        course.ifPresent(res::add);
        model.addAttribute("Course", res);
        return "CourseDescription";
    }

    @PostMapping("/CourseDescription")
    public String CourseUpdate(@Valid Course course)
    {
        Course originalmodel = courseRepository.findById(idCourse).orElse(null);
        if(course != null) {
            course.setIdCourse(idCourse);
            course.setNameOfCourse(originalmodel.getNameOfCourse());
            course.setDateOfCreation(originalmodel.getDateOfCreation());
            course.setCourseTypeID(originalmodel.getCourseTypeID());
            course.setFormOfEducationID(originalmodel.getFormOfEducationID());
            courseRepository.save(course);
        }
        return "redirect:/CourseDetails";
    }

    @GetMapping("/blockAddSample")
    public String getBlock(@RequestParam("num") String num, Model model) {
        model.addAttribute("num", num);
        return "blockAddSample :: copy"; // Это возвращает имя шаблона Thymeleaf и фрагмент, который нужно заменить в HTML коде
    }

    @GetMapping("/lectureAddSample")
    public String getLecture(@RequestParam("num") String num, @RequestParam("nameOfLectur") String nameOfLectur, Model model) {
        model.addAttribute("num", num);
        model.addAttribute("nameOfLectur", nameOfLectur);
        return "lectureAddSample :: copy1"; // Это возвращает имя шаблона Thymeleaf и фрагмент, который нужно заменить в HTML коде
    }

    @RequestMapping(value = "/blockAddSample", method = RequestMethod.POST)
    @ResponseBody
    public String handleBlockAddSampl(@RequestBody Map<String, Object> data) {
        List<String> nameOfBlock = (List<String>) data.get("nameOfBlock");
        List<String> description = (List<String>) data.get("description");
        List<String> duration = (List<String>) data.get("duration");
        List<String> nameOfLecture = (List<String>) data.get("nameOfLecture");
        List<String> blockNum = (List<String>) data.get("blockNum");
        List<String> blockTrueNum = (List<String>) data.get("blockTrueNum");
        for(int i = 0; i < nameOfBlock.size(); i++){
            Block block = new Block();
            Optional<Course> course = courseRepository.findById(idCourse);
            block.setNameOfBlock(nameOfBlock.get(i));
            block.setDescription(description.get(i));
            block.setDuration(duration.get(i));
            String blockTrueNum1 = blockTrueNum.get(i);
            block.setCourseID(course.get());
            blockRepository.save(block);
            for(int j = 0; j < nameOfLecture.size(); j++) {
                Lecture lecture = new Lecture();
                Optional<Block> block1 = blockRepository.findById(block.getIdBlock());
                String blockTrueNum2 = blockNum.get(j);
                if (blockTrueNum1.equals(blockTrueNum2)) {
                    lecture.setNameOfLecture(nameOfLecture.get(j));
                    lecture.setBlockID(block1.get());
                    lectureRepository.save(lecture);
                }
            }
        }
        return "OK";
    }

    @RequestMapping(value = "/check-blocks", method = RequestMethod.POST)
    @ResponseBody
    public List<Block> checkBlocks() {
        Optional<Course> course = courseRepository.findById(idCourse);
        List<Block> blocks = blockRepository.findBlocksByCourseID(course.get()) ; // blockRepository - экземпляр репозитория, отвечающего за блоки в базе данных

        return blocks ;
    }
    @RequestMapping(value = "/check-lectures/{id}", method = RequestMethod.POST)
    @ResponseBody
    public List<Lecture> checkLectures(@PathVariable("id") Long idBlock) {
        Optional<Block> block = blockRepository.findById(idBlock);
        List<Lecture> lectures = lectureRepository.findLecturesByBlockID(block.get()); // blockRepository - экземпляр репозитория, отвечающего за блоки в базе данных

        return lectures ;
    }

    @DeleteMapping("/blocks/{id}")
    public ResponseEntity<?> deleteBlockById(@PathVariable("id") Long idBlock) {
        try {
            Block block = blockRepository.findById(idBlock).orElseThrow();
            blockRepository.delete(block);
            // Возвращаем успешный код ответа HTTP 204 No Content
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Возвращаем код ответа HTTP 500 Internal Server Error в случае ошибки
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    long idBlock =0;
    @GetMapping("/CreateNewCourse/details/Block/Edit")
    public String CourseDetailsEdit(Model model)
    {
        if(!blockRepository.existsById(idBlock)){
            return "redirect:/Block";
        }
        Optional<Block> block = blockRepository.findById(idBlock);
        ArrayList<Block> res = new ArrayList<>();
        block.ifPresent(res::add);
        model.addAttribute("Block", res);
//        Optional<Course> course = courseRepository.findById(idCourse);
//        model.addAttribute("Course",course);
        return "CourseDetailsBlockEdit";
    }
    @RequestMapping(value="/CreateNewCourse/details/Block/Edit", method=RequestMethod.POST)
    @ResponseBody
    public int CourseDetailsEditt(@RequestParam("id") int id, Model model) {
        idBlock = id;
        model.addAttribute("id", id);
        return id;
    }
    @RequestMapping(value = "/handleBlockEdit", method = RequestMethod.POST)
    @ResponseBody
    public String handleBlockEdit(@RequestBody Map<String, Object> data) {
        List<String> nameOfBlock = (List<String>) data.get("nameOfBlock");
        List<String> description = (List<String>) data.get("description");
        List<String> duration = (List<String>) data.get("duration");
        for(int i = 0; i < nameOfBlock.size(); i++){
            Block block = blockRepository.findBlockByIdBlock(idBlock);
            Optional<Course> course = courseRepository.findById(idCourse);
            block.setNameOfBlock(nameOfBlock.get(i));
            block.setDescription(description.get(i));
            block.setDuration(duration.get(i));
            block.setCourseID(course.get());
            blockRepository.save(block);
        }
        return "OK";
    }
    @DeleteMapping("/lecture/{id}")
    public ResponseEntity<?> deleteLectureById(@PathVariable("id") Long idLecture) {
        try {
            Lecture lecture = lectureRepository.findById(idLecture).orElseThrow();
            lectureRepository.delete(lecture);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}