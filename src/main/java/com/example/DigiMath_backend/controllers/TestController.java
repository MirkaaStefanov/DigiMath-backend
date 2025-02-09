package com.example.DigiMath_backend.controllers;

import com.example.DigiMath_backend.dtos.TestDTO;
import com.example.DigiMath_backend.services.TestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {
    private final TestService testService;

    @PostMapping("/create")
    public ResponseEntity<TestDTO> createGame(@Valid @RequestBody TestDTO test, @RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(testService.createTest(test));
    }
}
