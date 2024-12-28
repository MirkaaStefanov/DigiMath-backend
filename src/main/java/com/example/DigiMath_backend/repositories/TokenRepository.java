package com.example.DigiMath_backend.repositories;


import com.example.DigiMath_backend.models.Token;
import com.example.DigiMath_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUser(User user);

    Optional<Token> findByToken(String token);
}

