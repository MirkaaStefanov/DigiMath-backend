package com.example.DigiMath_backend.exeptions;

public class CustomEmailException extends RuntimeException {
    public CustomEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
