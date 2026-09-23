package com.example.demo.exception;

public class NoNamesFoundException extends RuntimeException {
    public NoNamesFoundException(String message) {
        super(message);
    }
}
