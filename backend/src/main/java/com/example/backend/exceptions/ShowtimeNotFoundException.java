package com.example.backend.exceptions;

public class ShowtimeNotFoundException extends RuntimeException {
    public ShowtimeNotFoundException(String message) {
        super(message);
    }
}
