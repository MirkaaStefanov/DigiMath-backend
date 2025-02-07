package com.example.DigiMath_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PascalDTO {

    private Long id;
    private String first;
    private String second;
    private int coefficient;

}
