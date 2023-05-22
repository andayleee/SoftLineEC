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
/**
 * Контроллер для работы с тестами к лекциям курса.
 */
@Controller
public class TestController {
    /**
     * Репозиторий лекций.
     */
    @Autowired
    private LectureRepository lectureRepository;
    /**
     * Репозиторий тестов.
     */
    @Autowired
    private TestRepository testRepository;
    /**
     * Обрабатывает GET-запрос на получение списка всех тестов.
     * @param model объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы со списком тестов
     */
    @GetMapping("/Test")
    public String Test(Model model)
    {
        Iterable<Test> test = testRepository.findAll();
        model.addAttribute("Test", test);
        return "TestMain";
    }
    /**
     * Обрабатывает GET-запрос на получение страницы добавления нового теста.
     * @param Test объект Test, используемый для передачи данных на страницу
     * @param addr объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы добавления нового теста
     */
    @GetMapping("/TestAdd")
    public String TestAdd(@ModelAttribute("Test") Test Test, Model addr)
    {
        Iterable<Lecture> nameOfLecture = lectureRepository.findAll();
        addr.addAttribute("nameOfLecture",nameOfLecture);
        return "TestAdd";
    }
    /**
     * Обрабатывает POST-запрос на добавление нового теста.
     * @param Test объект Test, содержащий данные о тесте
     * @param bindingResult объект BindingResult, содержащий результаты валидации данных
     * @param nameOfLecture строка, содержащая наименование лекции, к которой относится тест
     * @param addr объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы добавления нового теста или перенаправление на страницу со списком тестов
     */
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
    /**
     * Обрабатывает GET-запрос на редактирование существующего теста.
     * @param id идентификатор теста для редактирования
     * @param model объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы редактирования теста
     */
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
    /**
     * Обрабатывает POST-запрос на сохранение измененных данных о тесте.
     * @param id идентификатор теста для сохранения изменений
     * @param test объект Test, содержащий измененные данные о тесте
     * @param bindingResult объект BindingResult, содержащий результаты валидации данных
     * @param nameOfLecture строка, содержащая наименование лекции, к которой относится тест
     * @return перенаправление на страницу со списком тестов
     */
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
    /**
     * Обрабатывает GET-запрос на удаление теста.
     * @param id идентификатор теста для удаления
     * @param model объект Model, используемый для передачи данных на страницу
     * @return перенаправление на страницу со списком тестов после удаления теста
     */
    @GetMapping("/Test/{id}/remove")
    public String TestRemove(@PathVariable("id") long id, Model model)
    {
        Test test = testRepository.findById(id).orElseThrow();
        testRepository.delete(test);
        return "redirect:/Test";
    }
}
