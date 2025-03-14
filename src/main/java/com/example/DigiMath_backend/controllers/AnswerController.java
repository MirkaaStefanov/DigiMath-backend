package com.example.DigiMath_backend.controllers;

import com.example.DigiMath_backend.dtos.AnswerDTO;
import com.example.DigiMath_backend.services.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
@AllArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/save")
    public ResponseEntity<AnswerDTO> save(@RequestBody AnswerDTO answerDTO, @RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(answerService.save(answerDTO));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<AnswerDTO> findById(@PathVariable Long id, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(answerService.findById(id));
    }

}
