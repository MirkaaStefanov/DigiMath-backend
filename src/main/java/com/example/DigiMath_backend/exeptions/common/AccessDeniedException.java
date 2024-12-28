package com.example.DigiMath_backend.exeptions.common;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends ApiException {
    public AccessDeniedException() {
        super("Access Denied", HttpStatus.FORBIDDEN);
    }
}
