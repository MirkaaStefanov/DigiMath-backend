package com.example.DigiMath_backend.services;

import com.example.DigiMath_backend.dtos.TestDTO;
import com.example.DigiMath_backend.models.Test;
import com.example.DigiMath_backend.repositories.TestRepository;
import com.example.DigiMath_backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    public TestDTO createTest(TestDTO testDTO) {
        Test test = modelMapper.map(testDTO, Test.class);
        testRepository.save(test);
        return testDTO;
    }

    public List<TestDTO> findAll(){
        List<Test> allTests = testRepository.findAll();
        return allTests.stream()
                .map(test -> modelMapper.map(test, TestDTO.class))
                .collect(Collectors.toList());
    }

    public TestDTO getTestById(Long id) {
        Optional<Test> test = testRepository.findById(id);
        return test.map(value -> modelMapper.map(value, TestDTO.class))
                .orElseThrow(() -> new RuntimeException("Test not found with id: " + id));
    }

    public TestDTO updateTest(Long id, TestDTO updatedTestDTO) {
        return testRepository.findById(id)
                .map(test -> {
                    modelMapper.map(updatedTestDTO, test);
                    Test savedTest = testRepository.save(test);
                    return modelMapper.map(savedTest, TestDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Test not found with id: " + id));
    }

    // Delete
    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }
}
