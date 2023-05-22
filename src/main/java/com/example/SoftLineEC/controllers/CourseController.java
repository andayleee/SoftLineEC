package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.*;
import com.example.SoftLineEC.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Контроллер, обрабатывающий запросы для курсов.
 */
@Controller
public class CourseController {
    /**
     * Репозиторий курсов.
     */
    @Autowired
    private CourseRepository courseRepository;
    /**
     * Репозиторий типов курсов.
     */
    @Autowired
    private CourseTypeRepository courseTypeRepository;
    /**
     * Репозиторий форм обучения.
     */
    @Autowired
    private FormOfEducationRepository formOfEducationRepository;
    /**
     * Репозиторий тем курсов.
     */
    @Autowired
    private ThemeRepository themeRepository;
    /**
     * Репозиторий пользователей.
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * Обрабатывает GET-запрос на получение списка всех курсов.
     * @param search строка поиска.
     * @param model объект, используемый для передачи данных в представление.
     * @return имя представления для отображения списка курсов.
     */
    @GetMapping("/Course")
    public String Course(@RequestParam(defaultValue = "") String search, Model model)
    {
        if (search.equals("")) {
            Iterable<Course> course = courseRepository.findAll();
            model.addAttribute("Course", course);
        }
        return "CourseMain";
    }
    /**
     * Обрабатывает GET-запрос на добавление нового курса.
     * @param Course объект курса.
     * @param addr объект, используемый для передачи данных в представление.
     * @return имя представления для добавления нового курса.
     */
    @GetMapping("/CourseAdd")
    public String CourseAdd(@ModelAttribute("Course") Course Course, Model addr)
    {
        Iterable<CourseType> courseType = courseTypeRepository.findAll();
        addr.addAttribute("CourseTypes",courseType);
        Iterable<FormOfEducation> formOfEducations = formOfEducationRepository.findAll();
        addr.addAttribute("FormOfEducations",formOfEducations);
        Iterable<Theme> themes = themeRepository.findAll();
        addr.addAttribute("Theme",themes);
        return "CourseAdd";
    }
    /**
     * Обрабатывает POST-запрос на добавление нового курса.
     * @param course объект курса.
     * @param bindingResult результаты валидации данных формы.
     * @param nameOfCourseType название типа курса.
     * @param typeOfEducation тип обучения.
     * @param nameOfTheme название темы курса.
     * @param addr объект, используемый для передачи данных в представление.
     * @param session объект, представляющий сессию пользователя.
     * @return перенаправление на страницу списка курсов.
     */
    @PostMapping("/CourseAdd")
    public String CourseAddAdd(@ModelAttribute("Course") @Valid Course course, BindingResult bindingResult,
                               @RequestParam String nameOfCourseType, @RequestParam String typeOfEducation, @RequestParam String nameOfTheme, Model addr, HttpSession session)
    {
        if (bindingResult.hasErrors())
        {
            Iterable<CourseType> courseType = courseTypeRepository.findAll();
            addr.addAttribute("CourseTypes",courseType);
            Iterable<FormOfEducation> formOfEducations = formOfEducationRepository.findAll();
            addr.addAttribute("FormOfEducations",formOfEducations);
            Iterable<Theme> themes = themeRepository.findAll();
            addr.addAttribute("Theme",themes);
            return "CourseAdd";
        }
        Long idUser = (Long) session.getAttribute("idUser");
        course.setCourseTypeID(courseTypeRepository.findByNameOfCourseType(nameOfCourseType));
        course.setFormOfEducationID(formOfEducationRepository.findByTypeOfEducation(typeOfEducation));
        course.setThemeID(themeRepository.findThemeByNameOfTheme(nameOfTheme));
        course.setUserID(userRepository.findUserByid(idUser));
        courseRepository.save(course);
        return "redirect:/Course";
    }
    /**
     * Обрабатывает GET-запрос на редактирование существующего курса.
     * @param idCourse идентификатор курса.
     * @param model объект, используемый для передачи данных в представление.
     * @return имя представления для редактирования курса.
     */
    @GetMapping("/Course/{idCourse}/edit")
    public String CourseEdit(@PathVariable(value = "idCourse") long idCourse, Model model)
    {
        if(!courseRepository.existsById(idCourse)){
            return "redirect:/Course";
        }
        Optional<Course> course = courseRepository.findById(idCourse);
        ArrayList<Course> res = new ArrayList<>();
        course.ifPresent(res::add);
        model.addAttribute("Course", res);
        Iterable<CourseType> courseType = courseTypeRepository.findAll();
        model.addAttribute("CourseTypes",courseType);
        Iterable<FormOfEducation> formOfEducations = formOfEducationRepository.findAll();
        model.addAttribute("FormOfEducations",formOfEducations);
        Iterable<Theme> themes = themeRepository.findAll();
        model.addAttribute("Theme",themes);
        return "CourseEdit";
    }
    /**
     * Обрабатывает POST-запрос на обновление существующего курса.
     * @param idCourse идентификатор курса.
     * @param course объект курса.
     * @param bindingResult результаты валидации данных формы.
     * @param nameOfCourseType название типа курса.
     * @param typeOfEducation тип обучения.
     * @param nameOfTheme название темы курса.
     * @param session объект, представляющий сессию пользователя.
     * @return перенаправление на страницу списка курсов.
     */
    @PostMapping("/Course/{idCourse}/edit")
    public String CourseUpdate(@PathVariable("idCourse")long idCourse, @ModelAttribute("Course")
                               @Valid Course course, BindingResult bindingResult, @RequestParam String nameOfCourseType,@RequestParam String typeOfEducation, @RequestParam String nameOfTheme, HttpSession session)
    {
        if (bindingResult.hasErrors())
            return "CourseEdit";
        course.setCourseTypeID(courseTypeRepository.findByNameOfCourseType(nameOfCourseType));
        course.setFormOfEducationID(formOfEducationRepository.findByTypeOfEducation(typeOfEducation));
        course.setThemeID(themeRepository.findThemeByNameOfTheme(nameOfTheme));
        Long idUser = (Long) session.getAttribute("idUser");
        course.setUserID(userRepository.findUserByid(idUser));
        courseRepository.save(course);
        return "redirect:/Course";
    }
    /**
     * Обрабатывает GET-запрос на удаление курса.
     * @param idCourse идентификатор курса.
     * @param model объект, используемый для передачи данных в представление.
     * @return перенаправление на страницу списка курсов.
     */
    @GetMapping("/Course/{idCourse}/remove")
    public String CourseRemove(@PathVariable("idCourse") long idCourse, Model model)
    {
        Course course = courseRepository.findById(idCourse).orElseThrow();
        courseRepository.delete(course);
        return "redirect:/Course";
    }
    /**
     * Обрабатывает POST-запрос на поиск курсов по заданной строке.
     * @param search строка поиска.
     * @param model объект, используемый для передачи данных в представление.
     * @return имя представления для отображения списка найденных курсов.
     */
    @PostMapping("/CourseSearch")
    public String CourseSearch(@RequestParam(defaultValue = "") String search, Model model)
    {
        List<Course> result1 = courseRepository.findByCategoriesOfStudentsss(search);
        model.addAttribute("Course", result1);
        return Course(search,model);
    }
}
