package com.example.DigiMath_backend.services.security;

import com.example.DigiMath_backend.dtos.auth.AuthenticationRequest;
import com.example.DigiMath_backend.dtos.auth.AuthenticationResponse;
import com.example.DigiMath_backend.dtos.auth.PublicUserDTO;
import com.example.DigiMath_backend.dtos.auth.RefreshTokenBodyDTO;
import com.example.DigiMath_backend.dtos.auth.RegisterRequest;
import com.example.DigiMath_backend.enums.TokenType;
import com.example.DigiMath_backend.exeptions.EmailNotVerified;
import com.example.DigiMath_backend.exeptions.InvalidTokenException;
import com.example.DigiMath_backend.exeptions.UserLoginException;
import com.example.DigiMath_backend.models.Token;
import com.example.DigiMath_backend.models.User;
import com.example.DigiMath_backend.models.VerificationToken;
import com.example.DigiMath_backend.repositories.UserRepository;
import com.example.DigiMath_backend.repositories.VerificationTokenRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserServiceAuthentication userService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = userService.createUser(request);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenService.saveToken(user, jwtToken, TokenType.ACCESS);
        tokenService.saveToken(user, refreshToken, TokenType.REFRESH);

        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(modelMapper.map(user, PublicUserDTO.class))
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException exception) {
            throw new UserLoginException();
        }

        User user = userService.findByEmail(request.getEmail());

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        userService.updateStreak(user);

        tokenService.revokeAllUserTokens(user);
        tokenService.saveToken(user, jwtToken, TokenType.ACCESS);
        tokenService.saveToken(user, refreshToken, TokenType.REFRESH);

        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(modelMapper.map(user, PublicUserDTO.class))
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenBodyDTO refreshTokenBodyDTO) {
        final String refreshToken = refreshTokenBodyDTO.getRefreshToken();

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new InvalidTokenException();
        }

        String userEmail;

        try {
            userEmail = jwtService.extractUsername(refreshToken);
        } catch (JwtException exception) {
            throw new InvalidTokenException();
        }

        if (userEmail == null) {
            throw new InvalidTokenException();
        }

        // Make sure token is a refresh token not an access token
        Token token = tokenService.findByToken(refreshToken);
        if (token != null && token.tokenType != TokenType.REFRESH) {
            throw new InvalidTokenException();
        }

        User user = userService.findByEmail(userEmail);

        if (!jwtService.isTokenValid(refreshToken, user)) {
            tokenService.revokeToken(token);
            throw new InvalidTokenException();
        }

        String accessToken = jwtService.generateToken(user);

        tokenService.revokeAllUserTokens(user);
        tokenService.saveToken(user, accessToken, TokenType.ACCESS);
        tokenService.saveToken(user, refreshToken, TokenType.REFRESH);

        return AuthenticationResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Resets the password for a user based on the provided token and new password.
     */
    @Override
    public void resetPassword(String token, String newPassword) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new InvalidTokenException();
        }


        User user = verificationToken.getUser();
        if (user == null) {
            throw new InvalidTokenException();
        }

        verificationToken.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
    }

    @Override
    public User forgotPassword(String email) {
        User user = userService.findByEmail(email);

        if (!user.isEnabled()) {
            throw new EmailNotVerified();
        }

        return user;
    }

}
