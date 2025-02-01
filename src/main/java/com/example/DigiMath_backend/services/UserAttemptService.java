package com.example.DigiMath_backend.services;

import com.example.DigiMath_backend.dtos.UserAttemptDTO;
import com.example.DigiMath_backend.dtos.UserDTO;
import com.example.DigiMath_backend.models.Test;
import com.example.DigiMath_backend.models.User;
import com.example.DigiMath_backend.models.UserAttempt;
import com.example.DigiMath_backend.repositories.TestRepository;
import com.example.DigiMath_backend.repositories.UserAttemptRepository;
import com.example.DigiMath_backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class UserAttemptService {

    private final UserAttemptRepository userAttemptRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    // Create
    public UserAttemptDTO createUserAttempt(UserAttemptDTO userAttemptDTO) {
        UserAttempt userAttempt = modelMapper.map(userAttemptDTO, UserAttempt.class);

        UserDTO userDTO = userService.findAuthenticatedUser();
        User user = modelMapper.map(userDTO, User.class);

        userAttempt.setUser(user);

        Test test = testRepository.findById(userAttemptDTO.getTest().getId())
                .orElseThrow(() -> new RuntimeException("Test not found with id: " + userAttemptDTO.getTest().getId()));
        userAttempt.setTest(test);

        UserAttempt savedUserAttempt = userAttemptRepository.save(userAttempt);

        // Map the saved UserAttempt back to DTO and return
        return modelMapper.map(savedUserAttempt, UserAttemptDTO.class);
    }

    // Read (Single UserAttempt)
    public UserAttemptDTO getUserAttemptById(Long id) {
        Optional<UserAttempt> userAttempt = userAttemptRepository.findById(id);
        return userAttempt.map(value -> modelMapper.map(value, UserAttemptDTO.class))
                .orElseThrow(() -> new RuntimeException("UserAttempt not found with id: " + id));
    }

    // Read (All UserAttempts)
    public List<UserAttemptDTO> getAllUserAttempts() {
        List<UserAttempt> userAttempts = userAttemptRepository.findAll();
        return userAttempts.stream()
                .map(userAttempt -> modelMapper.map(userAttempt, UserAttemptDTO.class))
                .collect(Collectors.toList());
    }

    // Update
    public UserAttemptDTO updateUserAttempt(Long id, UserAttemptDTO updatedUserAttemptDTO) {
        return userAttemptRepository.findById(id)
                .map(userAttempt -> {


                    Test test = testRepository.findById(updatedUserAttemptDTO.getTest().getId())
                            .orElseThrow(() -> new RuntimeException("Test not found with id: " + updatedUserAttemptDTO.getTest().getId()));
                    userAttempt.setTest(test);

                    modelMapper.map(updatedUserAttemptDTO, userAttempt);

                    UserAttempt savedUserAttempt = userAttemptRepository.save(userAttempt);


                    return modelMapper.map(savedUserAttempt, UserAttemptDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("UserAttempt not found with id: " + id));
    }

    // Delete
    public void deleteUserAttempt(Long id) {
        UserAttempt userAttempt = userAttemptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserAttempt not found with id: " + id));

        userAttemptRepository.delete(userAttempt);
    }
}
