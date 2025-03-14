package com.example.DigiMath_backend.dtos;

import com.example.DigiMath_backend.enums.QuestionType;
import com.example.DigiMath_backend.models.Answer;
import com.example.DigiMath_backend.models.Test;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {


    private Long id;

    private TestDTO test;

    private String text;

    private QuestionType questionType;

}
