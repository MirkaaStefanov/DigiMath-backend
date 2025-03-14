package com.example.DigiMath_backend.dtos;

import com.example.DigiMath_backend.models.Question;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private Long id;

    private QuestionDTO question;

    private String text;

    private boolean isCorrect;
}
