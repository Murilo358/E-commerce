package com.ecommerceCatalog.CommerceCatalog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends GenericException {

    HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public NotFoundException(String objectType, Number identifier) {
        super("Could not find the " + objectType + " with the id: " + identifier, HttpStatus.NOT_FOUND);
    }

}
