package com.example.SoftLineEC.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
/**
 * Конфигурационный класс для MVC фреймворка.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /**
     * Регистрирует новый контроллер представления для страницы входа.
     * @param registry объект, используемый для регистрации новых контроллеров представления.
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("Login");
    }

}

