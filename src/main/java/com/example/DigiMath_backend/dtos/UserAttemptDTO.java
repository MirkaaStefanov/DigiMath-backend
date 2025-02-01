package com.example.DigiMath_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAttemptDTO {

    private Long id;

    private UserDTO user;

    private TestDTO test;

    private Date startedAt;

    private List<UserAnswerDTO> userAnswers;

    private double finalPoints;
}
