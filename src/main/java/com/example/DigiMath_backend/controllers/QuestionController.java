package com.example.DigiMath_backend.controllers;

import com.example.DigiMath_backend.dtos.QuestionDTO;
import com.example.DigiMath_backend.services.QuestionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/save")
    public ResponseEntity<QuestionDTO> save(@RequestBody QuestionDTO questionDTO, @RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(questionService.save(questionDTO));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<QuestionDTO> findById(@PathVariable Long id, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(questionService.findById(id));
    }

}
