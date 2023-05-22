package com.example.SoftLineEC.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
/**
 * Конфигурационный класс для приложения.
 */
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
    /**
     * Настраивает список конвертеров сообщений для отправки и получения запросов и ответов.
     * @param converters список конвертеров сообщений.
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converters.add(converter);
    }
}
