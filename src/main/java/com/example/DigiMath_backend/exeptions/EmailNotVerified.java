package com.example.DigiMath_backend.exeptions;

import com.example.DigiMath_backend.exeptions.common.ApiException;
import org.springframework.http.HttpStatus;

public class EmailNotVerified extends ApiException {
    public EmailNotVerified() {
        super("Не можете да влезете, имейлът, даден по време на регистрацията, не е потвърден!", HttpStatus.BAD_REQUEST);
    }
}
