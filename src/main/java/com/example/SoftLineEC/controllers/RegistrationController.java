package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Role;
import com.example.SoftLineEC.models.User;
import com.example.SoftLineEC.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;
/**
 * Контроллер для работы с регистрацией пользователей.
 */
@Controller
public class RegistrationController {
    /**
     * Репозиторий пользователей.
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * Интерфейс для кодирования паролей пользователей.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * Обрабатывает GET-запрос на получение страницы регистрации пользователя.
     * @param user объект User, используемый для передачи данных на страницу
     * @return имя представления для страницы регистрации пользователя
     */
    @GetMapping("/registration")
    private String RegView(@ModelAttribute("User") User user)
    {
        return "Registration";
    }
    /**
     * Обрабатывает POST-запрос на создание нового пользователя.
     * @param user объект User, содержащий данные о пользователе
     * @param bindingResult объект BindingResult, содержащий результаты валидации данных
     * @param model объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы регистрации или перенаправление на страницу входа в систему
     */
    @PostMapping("/registration")
    public String Reg(@ModelAttribute("User") @Valid User user, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors()) {
            return "Registration";
        }
        if (!user.getPassword().equals(user.getRepeatPassword())) {
            bindingResult.rejectValue("repeatPassword", "passwords.not.matching", "Passwords do not match");
            return "Registration";
        }
        User user_from_db = userRepository.findUserByUsername(user.getUsername());
        if(user_from_db != null)
        {
            model.addAttribute("message","Пользователь с таким логином уже существует");
            return "Registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRepeatPassword(user.getRepeatPassword());
        userRepository.save(user);

        return "redirect:/login";
    }
}
