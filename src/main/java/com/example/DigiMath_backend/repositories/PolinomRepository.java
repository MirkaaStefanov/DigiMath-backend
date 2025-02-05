package com.example.DigiMath_backend.repositories;

import com.example.DigiMath_backend.models.Polinom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolinomRepository extends JpaRepository<Polinom, Long> {
}
