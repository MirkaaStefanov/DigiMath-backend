package com.example.DigiMath_backend.exeptions;



import com.example.DigiMath_backend.exeptions.common.ApiException;
import org.springframework.http.HttpStatus;

public class UserLoginException extends ApiException {
    public UserLoginException() {
        super("Invalid email or password", HttpStatus.BAD_REQUEST);
    }
}