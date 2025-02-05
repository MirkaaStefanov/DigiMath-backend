package com.example.DigiMath_backend.controllers;

import com.example.DigiMath_backend.dtos.PolinomDTO;
import com.example.DigiMath_backend.services.PolinomService;
import com.example.DigiMath_backend.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Generated;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/polinom")
@AllArgsConstructor
public class PolinomController {

    private final PolinomService polinomService;

    @PostMapping("/save")
    public ResponseEntity<PolinomDTO> savePolinom(@RequestBody PolinomDTO polinomDTO){
        return ResponseEntity.ok(polinomService.createPolinom(polinomDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolinomDTO> findById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(polinomService.findById(id));
    }

    @GetMapping("/ifRoot")
    public ResponseEntity<List<Double>> ifRoot(@RequestParam List<Double> polinomCoef, @RequestParam double root){
        return ResponseEntity.ok(polinomService.ifRoot(polinomCoef,root));
    }

    @GetMapping("/theRoots")
    public ResponseEntity<List<String>> theRoots(@RequestParam List<Double> polinomCoef){
        return ResponseEntity.ok(polinomService.theRoots(polinomCoef));
    }

}
