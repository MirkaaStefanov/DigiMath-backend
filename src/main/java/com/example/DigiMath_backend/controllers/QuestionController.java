package com.example.DigiMath_backend.controllers;

import com.example.DigiMath_backend.dtos.QuestionDTO;
import com.example.DigiMath_backend.services.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


}
