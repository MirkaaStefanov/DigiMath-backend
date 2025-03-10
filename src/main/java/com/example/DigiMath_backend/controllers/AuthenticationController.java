package com.example.DigiMath_backend.controllers;



import com.example.DigiMath_backend.dtos.auth.AuthenticationRequest;
import com.example.DigiMath_backend.dtos.auth.AuthenticationResponse;
import com.example.DigiMath_backend.dtos.auth.RefreshTokenBodyDTO;
import com.example.DigiMath_backend.dtos.auth.RegisterRequest;
import com.example.DigiMath_backend.models.User;
import com.example.DigiMath_backend.services.security.AuthenticationService;
import com.example.DigiMath_backend.services.security.events.OnPasswordResetRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ApplicationEventPublisher eventPublisher;
    private final LogoutHandler logoutHandler;

    @Value("${server.backend.baseUrl}")
    private String appBaseUrl;

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

    @PostMapping("/forgot-password") // Sends link to email so the user can change their password
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        User user = authenticationService.forgotPassword(email);
        eventPublisher.publishEvent(new OnPasswordResetRequestEvent(user, appBaseUrl));
        return ResponseEntity.ok("Password reset link sent to your email!");
    }

    @PostMapping("/password-reset")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        authenticationService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }

}
