package com.example.DigiMath_backend.repositories;

import com.example.DigiMath_backend.models.UserAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;

@Repository
public interface UserAttemptRepository extends JpaRepository<UserAttempt, Long> {

}
