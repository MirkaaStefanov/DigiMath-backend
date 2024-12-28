package com.example.DigiMath_backend.controllers;



import com.example.DigiMath_backend.dtos.auth.AuthenticationRequest;
import com.example.DigiMath_backend.dtos.auth.AuthenticationResponse;
import com.example.DigiMath_backend.dtos.auth.RefreshTokenBodyDTO;
import com.example.DigiMath_backend.dtos.auth.RegisterRequest;
import com.example.DigiMath_backend.services.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutHandler logoutHandler;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenBodyDTO refreshTokenBody) throws IOException {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenBody));
    }

}
