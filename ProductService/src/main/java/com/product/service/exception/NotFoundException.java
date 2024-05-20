package com.product.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {

    HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public NotFoundException(String objectType, Object identifier) {
        super("Could not find: " + objectType + " with id: " + identifier);
    }

}
