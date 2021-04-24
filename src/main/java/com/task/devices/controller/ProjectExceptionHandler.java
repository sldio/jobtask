package com.task.devices.controller;

import com.task.devices.services.data.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = "com.task.devices.controller")
public class ProjectExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<String> handle(Exception e, HttpServletRequest request, HandlerMethod handlerMethod) {
        if(e instanceof AppException) {
            AppException appException = (AppException) e;
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(appException.getMessage());
        }

        //default:
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
