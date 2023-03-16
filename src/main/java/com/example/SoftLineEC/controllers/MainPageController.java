package com.example.SoftLineEC.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @GetMapping("/main")
    public String mainPage(Model model) {return "MainPage";}
}
