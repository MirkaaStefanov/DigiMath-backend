package com.example.DigiMath_backend.services.security;



import com.example.DigiMath_backend.dtos.auth.AuthenticationRequest;
import com.example.DigiMath_backend.dtos.auth.AuthenticationResponse;
import com.example.DigiMath_backend.dtos.auth.RefreshTokenBodyDTO;
import com.example.DigiMath_backend.dtos.auth.RegisterRequest;
import com.example.DigiMath_backend.models.User;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse refreshToken(RefreshTokenBodyDTO refreshTokenBodyDTO) throws IOException;

    void resetPassword(String token, String newPassword);

    User forgotPassword(String email);
}
