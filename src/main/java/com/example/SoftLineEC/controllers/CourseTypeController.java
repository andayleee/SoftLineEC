package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.CourseType;
import com.example.SoftLineEC.repositories.CourseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
/**
 * Контроллер, обрабатывающий запросы для типов курсов.
 */
@Controller
public class CourseTypeController {
    /**
     * Репозиторий типов курсов.
     */
    @Autowired
    private CourseTypeRepository courseTypeRepository;
    /**
     * Обрабатывает GET-запрос на получение списка всех типов курсов.
     * @param model объект, используемый для передачи данных в представление.
     * @return имя представления для отображения списка типов курсов.
     */
    @GetMapping("/CourseType")
    public String CourseType(Model model)
    {
        Iterable<CourseType> courseType = courseTypeRepository.findAll();
        model.addAttribute("CourseType", courseType);
        return "CourseTypeMain";
    }
    /**
     * Обрабатывает GET-запрос на добавление нового типа курса.
     * @param courseType объект типа курса.
     * @return имя представления для добавления нового типа курса.
     */
    @GetMapping("/CourseTypeAdd")
    public String CourseTypeAdd(@ModelAttribute ("CourseType") CourseType courseType)
    {
        return "CourseTypeAdd";
    }
    /**
     * Обрабатывает POST-запрос на добавление нового типа курса.
     * @param courseType объект типа курса.
     * @param bindingResult результаты валидации данных формы.
     * @return перенаправление на страницу списка типов курсов.
     */
    @PostMapping("/CourseTypeAdd")
    public String CourseTypeAddAdd(@ModelAttribute ("CourseType") @Valid CourseType courseType, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "CourseTypeAdd";
        }
        courseTypeRepository.save(courseType);
        return "redirect:/CourseType";
    }
    /**
     * Обрабатывает GET-запрос на редактирование существующего типа курса.
     * @param idCourseType идентификатор типа курса.
     * @param model объект, используемый для передачи данных в представление.
     * @return имя представления для редактирования типа курса.
     */
    @GetMapping("/CourseType/{idCourseType}/edit")
    public String CourseTypeEdit(@PathVariable("idCourseType") long idCourseType, Model model)
    {
        if(!courseTypeRepository.existsById(idCourseType)){
            return "redirect:/CourseType";
        }
        CourseType res = courseTypeRepository.findById(idCourseType).orElseThrow();
        model.addAttribute("CourseType", res);
        return "CourseTypeEdit";
    }
    /**
     * Обрабатывает POST-запрос на обновление существующего типа курса.
     * @param courseType объект типа курса.
     * @param bindingResult результаты валидации данных формы.
     * @param idCourseType идентификатор типа курса.
     * @return перенаправление на страницу списка типов курсов.
     */
    @PostMapping("/CourseType/{idCourseType}/edit")
    public String CourseTypeUpdate(@ModelAttribute("courseType") @Valid CourseType courseType, BindingResult bindingResult, @PathVariable("idCourseType")long idCourseType)
    {
        if (bindingResult.hasErrors())
        {
            return "CourseTypeEdit";
        }
        courseTypeRepository.save(courseType);
        return "redirect:/CourseType";
    }
    /**
     * Обрабатывает GET-запрос на удаление типа курса.
     * @param idCourseType идентификатор типа курса.
     * @param model объект, используемый для передачи данных в представление.
     * @return перенаправление на страницу списка типов курсов.
     */
    @GetMapping("/CourseType/{idCourseType}/remove")
    public String CourseTypeRemove(@PathVariable("idCourseType") long idCourseType, Model model)
    {
        CourseType courseType = courseTypeRepository.findById(idCourseType).orElseThrow();
        courseTypeRepository.delete(courseType);
        return "redirect:/CourseType";
    }
}
