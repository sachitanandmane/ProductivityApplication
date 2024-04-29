package com.productivity.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CustomeException> exceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException){
        return ResponseEntity.badRequest().body(new CustomeException(methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage()));
    }
//    @ExceptionHandler
//    public ResponseEntity<CustomeException> exceptionHandler2(HttpMessageNotReadableException httpMessageNotReadableException){
//        return  ResponseEntity.badRequest().body(new CustomeException("From exceptionHandler2."));
//    }
}
