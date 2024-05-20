package com.product.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<GenericException> handleGenericException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericException(ex.getMessage(), HttpStatus.BAD_REQUEST));
//    }

}
