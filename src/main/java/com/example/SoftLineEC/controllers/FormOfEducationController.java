package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.CourseType;
import com.example.SoftLineEC.models.FormOfEducation;
import com.example.SoftLineEC.repositories.FormOfEducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
/**
 * Контроллер для управления формами обучения.
 */
@Controller
public class FormOfEducationController {
    /**
     * Репозиторий форм обучения.
     */
    @Autowired
    private FormOfEducationRepository formOfEducationRepository;
    /**
     * Обрабатывает GET-запрос на получение страницы со всеми формами обучения.
     * @param model объект Model, используемый для передачи данных в представление
     * @return имя представления, отображающего страницу со всеми формами обучения
     */
    @GetMapping("/FormOfEducation")
    public String FormOfEducation(Model model)
    {
        Iterable<FormOfEducation> formOfEducation = formOfEducationRepository.findAll();
        model.addAttribute("FormOfEducation", formOfEducation);
        return "FormOfEducationMain";
    }
    /**
     * Обрабатывает GET-запрос на получение страницы для добавления новой формы обучения.
     * @param formOfEducation объект FormOfEducation, используемый для сохранения данных о новой форме обучения
     * @return имя представления, отображающего страницу для добавления новой формы обучения
     */
    @GetMapping("/FormOfEducationAdd")
    public String FormOfEducationAdd(@ModelAttribute("FormOfEducation") FormOfEducation formOfEducation)
    {
        return "FormOfEducationAdd";
    }
    /**
     * Обрабатывает POST-запрос на добавление новой формы обучения.
     * @param formOfEducation объект FormOfEducation, содержащий данные о новой форме обучения
     * @param bindingResult объект BindingResult, содержащий ошибки валидации
     * @return имя представления со всеми формами обучения в случае успешного добавления, иначе - страница для добавления новой формы обучения
     */
    @PostMapping("/FormOfEducationAdd")
    public String FormOfEducationAddAdd(@ModelAttribute("FormOfEducation") @Valid FormOfEducation formOfEducation, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "FormOfEducationAdd";
        }
        formOfEducationRepository.save(formOfEducation);
        return "redirect:/FormOfEducation";
    }
    /**
     * Обрабатывает GET-запрос на получение страницы для редактирования формы обучения.
     * @param idFormOfEducation идентификатор формы обучения, которую необходимо отредактировать
     * @param model объект Model, используемый для передачи данных в представление
     * @return имя представления, отображающего страницу для редактирования формы обучения
     */
    @GetMapping("/FormOfEducation/{idFormOfEducation}/edit")
    public String FormOfEducationEdit(@PathVariable("idFormOfEducation") long idFormOfEducation, Model model)
    {
        if(!formOfEducationRepository.existsById(idFormOfEducation)){
            return "redirect:/FormOfEducation";
        }
        FormOfEducation res = formOfEducationRepository.findById(idFormOfEducation).orElseThrow();
        model.addAttribute("FormOfEducation", res);
        return "FormOfEducationEdit";
    }
    /**
     * Обрабатывает POST-запрос на редактирование формы обучения.
     * @param idFormOfEducation идентификатор формы обучения, которую необходимо отредактировать
     * @param formOfEducation объект FormOfEducation, содержащий данные о форме обучения после редактирования
     * @param bindingResult объект BindingResult, содержащий ошибки валидации
     * @return имя представления со всеми формами обучения в случае успешного редактирования, иначе - страница для редактирования формы обучения
     */
    @PostMapping("/FormOfEducation/{idFormOfEducation}/edit")
    public String FormOfEducationUpdate(@PathVariable("idFormOfEducation")long idFormOfEducation, @Valid FormOfEducation formOfEducation, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "FormOfEducationEdit";
        }
        formOfEducationRepository.save(formOfEducation);
        return "redirect:/FormOfEducation";
    }
    /**
     * Обрабатывает GET-запрос на удаление формы обучения.
     * @param idFormOfEducation идентификатор формы обучения, которую необходимо удалить
     * @param model объект Model, используемый для передачи данных в представление
     * @return имя представления со всеми формами обучения после удаления формы обучения
     */
    @GetMapping("/FormOfEducation/{idFormOfEducation}/remove")
    public String FormOfEducationRemove(@PathVariable("idFormOfEducation") long idFormOfEducation, Model model)
    {
        FormOfEducation formOfEducation = formOfEducationRepository.findById(idFormOfEducation).orElseThrow();
        formOfEducationRepository.delete(formOfEducation);
        return "redirect:/FormOfEducation";
    }
}
