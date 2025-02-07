package com.example.DigiMath_backend.repositories;

import com.example.DigiMath_backend.models.Pascal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PascalRepository extends JpaRepository<Pascal, Long> {
}
