package com.example.DigiMath_backend.models;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "polinoms")
public class Polinom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "polinom_id")
    private Long id;

    private int degree;

    @ElementCollection
    @CollectionTable(name = "polinom_coefficients", joinColumns = @JoinColumn(name = "polinom_id"))
    @Column(name = "coefficients")
    private List<Double> coefficients;

}
