package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProfessionAlreadyExistsException extends RuntimeException {
    public ProfessionAlreadyExistsException(String message) {
        super(message);
    }
}
