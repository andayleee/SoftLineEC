package com.example.SoftLineEC.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ErrorMessage handleException(Exception ex) {
//        ErrorMessage error = new ErrorMessage();
//        error.setMessage("Что-то пошло не так :/");
//        return error;
//    }

}

class ErrorMessage {
    private String message;
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
