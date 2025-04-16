package com.example.DigiMath_backend.services;

import com.example.DigiMath_backend.dtos.PascalDTO;
import com.example.DigiMath_backend.models.Pascal;
import com.example.DigiMath_backend.repositories.PascalRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class PascalService {

    private final PascalRepository pascalRepository;
    private final ModelMapper modelMapper;

    public PascalDTO savePascal(PascalDTO pascalDTO) {
        Pascal pascal = modelMapper.map(pascalDTO, Pascal.class);
        return modelMapper.map(pascalRepository.save(pascal), PascalDTO.class);
    }

    public List<List<Integer>> pyramid(int coef) throws ChangeSetPersister.NotFoundException {
        int koef = coef;
        List<List<Integer>> pyramid = new ArrayList<>();
        List<Integer> firstLine = new ArrayList<>();
        firstLine.add(1);
        pyramid.add(firstLine);

        for (int i = 1; i <= koef; i++) {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            for (int j = 0; j < pyramid.get(i - 1).size() - 1; j++) {
                int number = pyramid.get(i - 1).get(j) + pyramid.get(i - 1).get(j + 1);
                list.add(number);
            }
            list.add(1);
            pyramid.add(list);
        }
        return pyramid;
    }

    public String finalEquation(Long pascalId) throws ChangeSetPersister.NotFoundException {
        Pascal pascal = pascalRepository.findById(pascalId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        int koef = pascal.getCoefficient();
        String finalResult = "";
        List<List<Integer>> pyramid = pyramid(koef);

        String aInput = pascal.getFirst();
        String bInput = pascal.getSecond();
        boolean aIsNegative = aInput.startsWith("-");
        boolean bIsNegative = bInput.startsWith("-");

        String aSymbol = aIsNegative ? aInput.substring(1) : aInput;
        String bSymbol = bIsNegative ? bInput.substring(1) : bInput;

        Integer numA = null;
        Integer numB = null;

        try {
            numA = Integer.parseInt(aSymbol);
        } catch (Exception ignored) {
        }

        try {
            numB = Integer.parseInt(bSymbol);
        } catch (Exception ignored) {
        }

        List<Integer> lastLine = pyramid.get(koef);

        if (numA != null && numB != null) {

            int totalResult = 0;
            for (int i = 0; i < lastLine.size(); i++) {
                int coefficient = lastLine.get(i);
                int termValue = coefficient * (int) Math.pow(numA, koef - i) * (int) Math.pow(numB, i);
                if (aIsNegative && (koef - i) % 2 != 0) {
                    termValue = -termValue;
                }
                if (bIsNegative && i % 2 != 0) {
                    termValue = -termValue;
                }
                totalResult += termValue;
            }
            System.out.println("Result: " + totalResult);
            finalResult += totalResult;
            return finalResult;
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < lastLine.size(); i++) {
                int coefficient = lastLine.get(i);
                int powerA = koef - i;
                int powerB = i;

                int termCoefficient = coefficient;

                if (numA != null) {
                    termCoefficient *= Math.pow(numA, powerA);
                }
                if (numB != null) {
                    termCoefficient *= Math.pow(numB, powerB);
                }

                if (aIsNegative && powerA % 2 != 0) {
                    termCoefficient = -termCoefficient;
                }
                if (bIsNegative && powerB % 2 != 0) {
                    termCoefficient = -termCoefficient;
                }

                if (termCoefficient == -1 && (powerA > 0 || powerB > 0)) {
                    result.append("-");
                } else if (termCoefficient != 1 || (powerA == 0 && powerB == 0)) {
                    result.append(termCoefficient);
                }

                if (powerA > 0 && numA == null) {
                    result.append(aSymbol);
                    if (powerA > 1) {
                        result.append("^").append(powerA);
                    }
                }

                if (powerB > 0 && numB == null) {
                    result.append(bSymbol);
                    if (powerB > 1) {
                        result.append("^").append(powerB);
                    }
                }

                if (i < lastLine.size() - 1) {
                    result.append(" + ");
                }
            }
            finalResult = result.toString().replaceAll("\\+ -", "- ");

        }
        return finalResult;
    }

}
