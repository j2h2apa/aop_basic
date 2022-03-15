package com.cos.aopdemo.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

// Exception hooking!
@RestController
// Exception handller define
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public String argumentException(IllegalArgumentException e) {
        return "오류: " + e.getMessage();
    }

}
