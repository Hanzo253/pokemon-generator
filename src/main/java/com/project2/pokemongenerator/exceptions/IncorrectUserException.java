package com.project2.pokemongenerator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IncorrectUserException  extends RuntimeException {
    public IncorrectUserException(String message) {
        super(message);
    }
}
