package com.example.DigiMath_backend.repositories;

import com.example.DigiMath_backend.models.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer,Long> {
}
