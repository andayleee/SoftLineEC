package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.AnswerOptions;
import com.example.SoftLineEC.models.Question;
import com.example.SoftLineEC.models.Test;
import com.example.SoftLineEC.repositories.AnswerOptionsRepository;
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
 * Контроллер для взаимодействия с сущностью AnswerOptions.
 */
@Controller
public class AnswerOptionsController {
    /**
     * Интерфейс для выполнения операций с базой данных, связанных с сущностью Question.
     */
    @Autowired
    private QuestionRepository questionRepository;
    /**
     * Интерфейс для выполнения операций с базой данных, связанных с сущностью AnswerOptions.
     */
    @Autowired
    private AnswerOptionsRepository answerOptionsRepository;
    /**
     * Отображает страницу списка вариантов ответов.
     * @param model модель, содержащая список вариантов ответов.
     * @return имя представления для страницы списка вариантов ответов.
     */
    @GetMapping("/AnswerOptions")
    public String AnswerOptions(Model model)
    {
        Iterable<AnswerOptions> answerOptions = answerOptionsRepository.findAll();
        model.addAttribute("AnswerOptions", answerOptions);
        return "AnswerOptionsMain";
    }
    /**
     * Отображает страницу добавления нового варианта ответа.
     * @param AnswerOptions новый вариант ответа для добавления.
     * @param addr модель, содержащая список имён вопросов.
     * @return имя представления для страницы добавления варианта ответа.
     */
    @GetMapping("/AnswerOptionsAdd")
    public String AnswerOptionsAdd(@ModelAttribute("AnswerOptions") AnswerOptions AnswerOptions, Model addr)
    {
        Iterable<Question> nameOfQuestion= questionRepository.findAll();
        addr.addAttribute("nameOfQuestion",nameOfQuestion);
        return "AnswerOptionsAdd";
    }
    /**
     * Добавляет новый вариант ответа в базу данных.
     * @param AnswerOptions новый вариант ответа для добавления.
     * @param bindingResult результаты проверки модели.
     * @param nameOfQuestion имя соответствующего вопроса.
     * @param addr модель, содержащая список имён вопросов.
     * @return имя представления для страницы списка вариантов ответов.
     */
    @PostMapping("/AnswerOptionsAdd")
    public String AnswerOptionsAddAdd(@ModelAttribute("AnswerOptions") @Valid AnswerOptions AnswerOptions, BindingResult bindingResult,
                                 @RequestParam String nameOfQuestion, Model addr)
    {
        if (bindingResult.hasErrors()) {
            Iterable<Question> nameOfQuestionn= questionRepository.findAll();
            addr.addAttribute("nameOfQuestion",nameOfQuestionn);
            return "AnswerOptionsAdd";
        }
        AnswerOptions.setQuestionID(questionRepository.findByNameOfQuestion(nameOfQuestion));
        answerOptionsRepository.save(AnswerOptions);
        return "AnswerOptionsMain";
    }
    /**
     * Отображает страницу редактирования варианта ответа.
     * @param id идентификатор варианта ответа.
     * @param model модель, содержащая информацию о варианте ответа и список имён вопросов.
     * @return имя представления для страницы редактирования варианта ответа.
     */
    @GetMapping("/AnswerOptions/{id}/edit")
    public String AnswerOptionsEdit(@PathVariable(value = "id") long id, Model model)
    {
        Optional<AnswerOptions> answerOptions = answerOptionsRepository.findById(id);
        ArrayList<AnswerOptions> res = new ArrayList<>();
        answerOptions.ifPresent(res::add);
        model.addAttribute("AnswerOptions", res);
        Iterable<Question> question = questionRepository.findAll();
        model.addAttribute("Question",question);
        if(!answerOptionsRepository.existsById(id)){
            return "redirect:/AnswerOptions";
        }
        return "AnswerOptionsEdit";
    }
    /**
     * Обновляет данные варианта ответа в базе данных.
     * @param id идентификатор варианта ответа.
     * @param answerOptions вариант ответа для обновления.
     * @param bindingResult результаты проверки модели.
     * @param nameOfQuestion имя соответствующего вопроса.
     * @return перенаправление на страницу списка вариантов ответов.
     */
    @PostMapping("/AnswerOptions/{id}/edit")
    public String AnswerOptionsUpdate(@PathVariable("id")long id,
                                      @Valid AnswerOptions answerOptions, BindingResult bindingResult, @RequestParam String nameOfQuestion)
    {
        if (bindingResult.hasErrors())
            return "AnswerOptionsEdit";
        answerOptions.setQuestionID(questionRepository.findByNameOfQuestion(nameOfQuestion));
        answerOptionsRepository.save(answerOptions);
        return "redirect:/AnswerOptions";
    }
    /**
     * Удаляет вариант ответа из базы данных.
     * @param id идентификатор варианта ответа.
     * @param model модель, содержащая список вариантов ответов.
     * @return перенаправление на страницу списка вариантов ответов.
     */
    @GetMapping("/AnswerOptions/{id}/remove")
    public String AnswerOptionsRemove(@PathVariable("id") long id, Model model)
    {
        AnswerOptions answerOptions = answerOptionsRepository.findById(id).orElseThrow();
        answerOptionsRepository.delete(answerOptions);
        return "redirect:/AnswerOptions";
    }
}
