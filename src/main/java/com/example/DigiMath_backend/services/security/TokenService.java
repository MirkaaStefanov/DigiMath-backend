package com.example.DigiMath_backend.services.security;




import com.example.DigiMath_backend.enums.TokenType;
import com.example.DigiMath_backend.models.Token;
import com.example.DigiMath_backend.models.User;

import java.util.List;

public interface TokenService {
    Token findByToken(String jwt);

    List<Token> findByUser(User user);

    void saveToken(User user, String jwtToken, TokenType tokenType);

    void revokeToken(Token token);

    void revokeAllUserTokens(User user);

    void logoutToken(String jwt);
}
