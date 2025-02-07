package com.example.DigiMath_backend.controllers;

import com.example.DigiMath_backend.dtos.PascalDTO;
import com.example.DigiMath_backend.services.PascalService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pascal")
@AllArgsConstructor
public class PascalController {

    private final PascalService pascalService;

    @PostMapping("/save")
    public ResponseEntity<PascalDTO> savePascal(@RequestBody PascalDTO pascalDTO){
        return ResponseEntity.ok(pascalService.savePascal(pascalDTO));
    }
    @GetMapping("/result/{id}")
    public ResponseEntity<String> resultPascal(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(pascalService.finalEquation(id));
    }

}
