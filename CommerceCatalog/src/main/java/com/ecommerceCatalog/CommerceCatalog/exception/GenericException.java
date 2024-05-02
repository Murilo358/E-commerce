package com.ecommerceCatalog.CommerceCatalog.exception;

import org.springframework.http.HttpStatus;

public class GenericException extends RuntimeException{

    public HttpStatus httpStatus;

    public GenericException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }


}
