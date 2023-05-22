package com.example.SoftLineEC.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Глобальный обработчик исключений, который перехватывает все исключения, возникающие в контроллерах.
 * Возвращает объект ErrorMessage с сообщением об ошибке при возникновении исключения.
 */
@ControllerAdvice
public class CustomExceptionHandler {
    /**
     * Обрабатывает исключение Exception.
     * @param ex объект Exception, которое необходимо обработать
     * @return объект ErrorMessage, который содержит сообщение об ошибке
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorMessage handleException(Exception ex) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage("Что-то пошло не так :/");
        return error;
    }
}
/**
 * Класс, представляющий сообщение об ошибке.
 */
class ErrorMessage {
    private String message;
    /**
     * Устанавливает сообщение об ошибке.
     * @param message сообщение об ошибке
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * Возвращает сообщение об ошибке.
     * @return сообщение об ошибке
     */
    public String getMessage() {
        return message;
    }
}
