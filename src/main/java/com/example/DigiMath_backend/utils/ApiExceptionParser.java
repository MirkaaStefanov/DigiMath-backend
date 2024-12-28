package com.example.DigiMath_backend.utils;



import com.example.DigiMath_backend.dtos.ExceptionResponse;
import com.example.DigiMath_backend.exeptions.common.ApiException;

import java.time.LocalDateTime;

public class ApiExceptionParser {
    public static ExceptionResponse parseException(ApiException exception) {
        return ExceptionResponse
                .builder()
                .dateTime(LocalDateTime.now())
                .message(exception.getMessage())
                .status(exception.getStatus())
                .statusCode(exception.getStatusCode())
                .build();
    }
}
