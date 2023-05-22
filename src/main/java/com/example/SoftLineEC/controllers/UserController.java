package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Role;
import com.example.SoftLineEC.models.User;
import com.example.SoftLineEC.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
/**
 * Контроллер для работы с пользователями в режиме администратора.
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController {
    /**
     * Репозиторий пользователей.
     */
    @Autowired
    private com.example.SoftLineEC.repositories.UserRepository userRepository;
    /**
     * Обрабатывает GET-запрос на получение списка всех пользователей.
     * @param model объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы со списком пользователей
     */
    @GetMapping("/a")
    public String userView(Model model)
    {
        model.addAttribute("user_list", userRepository.findAll());
        return "UserMain";
    }
    /**
     * Обрабатывает GET-запрос на получение информации о конкретном пользователе.
     * @param id идентификатор пользователя, информацию о котором нужно получить
     * @param model объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы с информацией о пользователе
     */
    @GetMapping("/{id}")
    public String detailView(@PathVariable Long id, Model model)
    {
        model.addAttribute("user_object",userRepository.findById(id).orElseThrow());
        return "UserDetail";
    }
    /**
     * Обрабатывает GET-запрос на открытие формы редактирования информации о пользователе.
     * @param id идентификатор пользователя, информацию о котором нужно отредактировать
     * @param model объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы редактирования информации о пользователе
     */
    @GetMapping("/{id}/update")
    public String updView(@PathVariable(value = "id") Long id, Model model)
    {
        model.addAttribute("user_object",userRepository.findById(id).orElseThrow());
        model.addAttribute("roles", Role.values());
        return "UserEdit";
    }
    /**
     * Обрабатывает POST-запрос на сохранение измененной информации о пользователе.
     * @param user объект User, содержащий измененные данные о пользователе
     * @param bindingResult объект BindingResult, содержащий результаты валидации данных
     * @param modelRoles объект Model, используемый для передачи данных на страницу
     * @param roles массив строк, содержащий названия ролей, которые должны быть присвоены пользователю
     * @param id идентификатор пользователя, информацию о котором нужно отредактировать
     * @return перенаправление на страницу с информацией о пользователе после сохранения изменений
     */
    @PostMapping("/{id}/update")
    public String userPostUpdate(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult,
                                 Model modelRoles,
                                 @RequestParam( name="roles[]", required = false) String[] roles,
                                 @PathVariable( value = "id") long id)
    {
        if(bindingResult.hasErrors()) {
            modelRoles.addAttribute("user_object",userRepository.findById(id).orElseThrow());
            modelRoles.addAttribute("roles", Role.values());
            return "UserEdit";
        }
        Optional<User> userData = userRepository.findById(id);
        user.getRoles().clear();
        if(roles == null)
            return "UserEdit";
        if(roles != null)
        {
            for(String role: roles)
            {
                user.getRoles().add(Role.valueOf(role));
            }
        }
        user.setActive(true);
        user.setPassword(userData.get().getPassword());
        user.setRepeatPassword(userData.get().getRepeatPassword());
        user.setPhotoLink(userData.get().getPhotoLink());
        user.setPhoneNumber(userData.get().getPhoneNumber());
        user.setEdInstitution(userData.get().getEdInstitution());
        userRepository.save(user);
        return "redirect:/admin/{id}";
    }

}
