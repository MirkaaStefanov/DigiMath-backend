package com.example.DigiMath_backend.controllers;

import com.example.DigiMath_backend.dtos.TestDTO;
import com.example.DigiMath_backend.services.TestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/all")
    public ResponseEntity<List<TestDTO>>showAllTests( @RequestHeader("Authorization") String auth){
        return ResponseEntity.ok(testService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<TestDTO> createGame(@Valid @RequestBody TestDTO test, @RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(testService.createTest(test));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TestDTO> findById(@PathVariable Long id, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(testService.getTestById(id));
    }

}
