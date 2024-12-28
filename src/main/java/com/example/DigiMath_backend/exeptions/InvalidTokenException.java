package com.example.DigiMath_backend.exeptions;



import com.example.DigiMath_backend.exeptions.common.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ApiException {
    public InvalidTokenException() {
        super("Invalid token", HttpStatus.UNAUTHORIZED);
    }
}
