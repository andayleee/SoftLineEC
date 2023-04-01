package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.CourseType;
import com.example.SoftLineEC.models.FormOfEducation;
import com.example.SoftLineEC.repositories.BlockRepository;
import com.example.SoftLineEC.repositories.CourseRepository;
import com.example.SoftLineEC.repositories.CourseTypeRepository;
import com.example.SoftLineEC.repositories.FormOfEducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @RequestMapping(value = "/blockAddSample", method = RequestMethod.POST)
    public void handleBlockAddSampl(@RequestParam("nameOfBlock") List<String> nameOfBlockList, @RequestParam("description") List<String> input2List, @RequestParam("duration") List<String> input3List) {
        for(int i = 0; i < nameOfBlockList.size(); i++){
            Block block = new Block();
            Optional<Course> course = courseRepository.findById(idCourse);
            // Устанавливаем значения в модели данных из списка параметров запроса
            block.setNameOfBlock(nameOfBlockList.get(i));
            block.setDescription(input2List.get(i));
            block.setDuration(input3List.get(i));
            block.setCourseID(course.get());
            blockRepository.save(block);
        }
    }

}