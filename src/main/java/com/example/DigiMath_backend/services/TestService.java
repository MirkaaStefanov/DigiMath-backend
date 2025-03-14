package com.example.DigiMath_backend.services;

import com.example.DigiMath_backend.dtos.AnswerDTO;
import com.example.DigiMath_backend.dtos.QuestionDTO;
import com.example.DigiMath_backend.dtos.TestDTO;
import com.example.DigiMath_backend.models.Answer;
import com.example.DigiMath_backend.models.Question;
import com.example.DigiMath_backend.models.Test;
import com.example.DigiMath_backend.repositories.TestRepository;
import com.example.DigiMath_backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
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
        return modelMapper.map(testRepository.save(test), TestDTO.class);

    }

    public List<TestDTO> findAll() {
        List<Test> allTests = testRepository.findAll();

        List<TestDTO> testDTOS = allTests.stream()
                .map(test -> modelMapper.map(test, TestDTO.class)) // Map each User to UserDTO
                .collect(Collectors.toList());

        return testDTOS;
    }

    public TestDTO getTestById(Long id) throws ChangeSetPersister.NotFoundException {
        Test test = testRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return modelMapper.map(test, TestDTO.class);
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
