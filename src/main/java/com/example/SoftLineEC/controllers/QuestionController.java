package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Test;
import com.example.SoftLineEC.models.Question;
import com.example.SoftLineEC.repositories.QuestionRepository;
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
 * Контроллер для работы с вопросами тестов.
 */
@Controller
public class QuestionController {
    /**
     * Репозиторий тестов.
     */
    @Autowired
    private TestRepository testRepository;
    /**
     * Репозиторий вопросов.
     */
    @Autowired
    private QuestionRepository questionRepository;
    /**
     * Обрабатывает GET-запрос на получение списка вопросов.
     * @param model объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы со списком вопросов
     */
    @GetMapping("/Question")
    public String Question(Model model)
    {
        Iterable<Question> question = questionRepository.findAll();
        model.addAttribute("Question", question);
        return "QuestionMain";
    }
    /**
     * Обрабатывает GET-запрос на получение страницы добавления вопроса.
     * @param Question объект Question, используемый для передачи данных на страницу
     * @param addr объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы добавления вопроса
     */
    @GetMapping("/QuestionAdd")
    public String QuestionAdd(@ModelAttribute("Question") Question Question, Model addr)
    {
        Iterable<Test> nameOfTest= testRepository.findAll();
        addr.addAttribute("nameOfTest",nameOfTest);
        return "QuestionAdd";
    }
    /**
     * Обрабатывает POST-запрос на добавление вопроса.
     * @param Question объект Question, содержащий данные о вопросе
     * @param bindingResult объект BindingResult, содержащий результаты валидации данных
     * @param nameOfTest строка, содержащая имя теста для добавления вопроса
     * @param addr объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы со списком вопросов
     */
    @PostMapping("/QuestionAdd")
    public String QuestionAddAdd(@ModelAttribute("Question") @Valid Question Question, BindingResult bindingResult,
                             @RequestParam String nameOfTest, Model addr)
    {
        if (bindingResult.hasErrors()) {
            Iterable<Test> nameOfTestt= testRepository.findAll();
            addr.addAttribute("nameOfTest",nameOfTestt);
            return "QuestionAdd";
        }
        Question.setTestID(testRepository.findByNameOfTest(nameOfTest));
        questionRepository.save(Question);
        return "QuestionMain";
    }
    /**
     * Обрабатывает GET-запрос на получение страницы редактирования вопроса.
     * @param id идентификатор вопроса для редактирования
     * @param model объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы редактирования вопроса
     */
    @GetMapping("/Question/{id}/edit")
    public String QuestionEdit(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Question> question = questionRepository.findById(id);
        ArrayList<Question> res = new ArrayList<>();
        question.ifPresent(res::add);
        model.addAttribute("Question", res);
        Iterable<Test> test = testRepository.findAll();
        model.addAttribute("Test",test);
        if(!questionRepository.existsById(id)){
            return "redirect:/Question";
        }
        return "QuestionEdit";
    }
    /**
     * Обрабатывает POST-запрос на сохранение изменений вопроса.
     * @param id идентификатор вопроса для редактирования
     * @param question объект Question, содержащий данные о вопросе
     * @param bindingResult объект BindingResult, содержащий результаты валидации данных
     * @param nameOfTest строка, содержащая имя теста для добавления вопроса
     * @return имя представления для страницы со списком вопросов
     */
    @PostMapping("/Question/{id}/edit")
    public String QuestionUpdate(@PathVariable("id")long id,
                                 @Valid Question question, BindingResult bindingResult, @RequestParam String nameOfTest)
    {
        if (bindingResult.hasErrors())
            return "QuestionEdit";
        question.setTestID(testRepository.findByNameOfTest(nameOfTest));
        questionRepository.save(question);
        return "redirect:/Question";
    }
    /**
     * Обрабатывает GET-запрос на удаление вопроса.
     * @param id идентификатор вопроса для удаления
     * @param model объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы со списком вопросов
     */
    @GetMapping("/Question/{id}/remove")
    public String QuestionRemove(@PathVariable("id") long id, Model model)
    {
        Question question = questionRepository.findById(id).orElseThrow();
        questionRepository.delete(question);
        return "redirect:/Question";
    }
}
