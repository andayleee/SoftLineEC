package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.User;
import com.example.SoftLineEC.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
/**
 * Тесты для класса RegistrationController.
 */
public class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @Mock
    private PasswordEncoder passwordEncoder;
    /**
     * Подготовка перед выполнением каждого теста.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    /**
     * Тест метода Reg с валидным пользователем.
     * Ожидается перенаправление на страницу "/login".
     */
    @Test
    public void testReg_WithValidUser_RedirectToLogin() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRepeatPassword("password");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userRepository.findUserByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        String result = registrationController.Reg(user, bindingResult, model);
        assertEquals("redirect:/login", result);
    }
    /**
     * Тест метода Reg с невалидным пользователем.
     * Ожидается возврат вида "Registration".
     */
    @Test
    public void testReg_WithInvalidUser_ReturnRegistrationView() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRepeatPassword("differentpassword");

        when(bindingResult.hasErrors()).thenReturn(true);
        String result = registrationController.Reg(user, bindingResult, model);

        assertEquals("Registration", result);
    }
}

