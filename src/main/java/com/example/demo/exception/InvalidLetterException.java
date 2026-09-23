package com.example.demo.exception;

public class InvalidLetterException extends RuntimeException {
    public InvalidLetterException(String message) {
        super(message);
    }
}
