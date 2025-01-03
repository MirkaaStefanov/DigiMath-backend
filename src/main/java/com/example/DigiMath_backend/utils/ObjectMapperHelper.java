package com.example.DigiMath_backend.utils;

import com.example.DigiMath_backend.dtos.ExceptionResponse;
import com.example.DigiMath_backend.exeptions.common.ApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;

public class ObjectMapperHelper {
    public static void writeExceptionToObjectMapper(
            ObjectMapper objectMapper,
            ApiException exception,
            HttpServletResponse httpServletResponse
    ) throws IOException {
        ExceptionResponse exceptionResponse = ApiExceptionParser.parseException(exception);
        httpServletResponse.setStatus(exceptionResponse.getStatusCode());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ServletOutputStream out = httpServletResponse.getOutputStream();
        objectMapper.writeValue(out, exceptionResponse);
    }
}
