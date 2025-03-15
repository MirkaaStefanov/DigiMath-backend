package com.example.DigiMath_backend.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class RootService {
    public List<Integer> simplifyRoot(int degree, int number) {
        List<Integer> result = new ArrayList<>();
        result.add(1);
        result.add(degree);
        result.add(number);

        if (degree <= 0 || number <= 0) {
            return result;
        }

        int root = (int) Math.round(Math.pow(number, 1.0 / degree));
        if (Math.pow(root, degree) == number) {
            result.set(0, root);
            result.set(1, 1);
            result.set(2, 1);
            return result;
        }

        int largestFactor = 1;
        for (int i = (int) Math.pow(number, 1.0 / degree); i >= 1; i--) {
            int power = (int) Math.pow(i, degree);
            if (number % power == 0) {
                largestFactor = i;
                break;
            }
        }

        int remainingNumber = number / (int) Math.pow(largestFactor, degree);

        result.set(0, largestFactor);
        result.set(1, degree);
        result.set(2, remainingNumber);

        return result;
    }

}
