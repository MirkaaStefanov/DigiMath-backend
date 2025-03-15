package com.example.DigiMath_backend.controllers;


import com.example.DigiMath_backend.services.RootService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/root")
@AllArgsConstructor
public class RootController {
    private final RootService rootService;

    @GetMapping("/simplifyRoot")
    public ResponseEntity<List<Integer>> simplifyRoot(
            @RequestParam int degree,
            @RequestParam int number
    ) {
        return ResponseEntity.ok(rootService.simplifyRoot(degree, number));
    }
}
