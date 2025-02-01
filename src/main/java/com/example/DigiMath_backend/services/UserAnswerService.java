package com.example.DigiMath_backend.services;

import com.example.DigiMath_backend.dtos.UserAnswerDTO;
import com.example.DigiMath_backend.models.Answer;
import com.example.DigiMath_backend.models.Question;
import com.example.DigiMath_backend.models.UserAnswer;
import com.example.DigiMath_backend.models.UserAttempt;
import com.example.DigiMath_backend.repositories.AnswerRepository;
import com.example.DigiMath_backend.repositories.QuestionRepository;
import com.example.DigiMath_backend.repositories.UserAnswerRepository;
import com.example.DigiMath_backend.repositories.UserAttemptRepository;
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
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserAttemptRepository userAttemptRepository;
    private final ModelMapper modelMapper;

    // Create
    public UserAnswerDTO createUserAnswer(UserAnswerDTO userAnswerDTO) {
        // Map DTO to Entity
        UserAnswer userAnswer = modelMapper.map(userAnswerDTO, UserAnswer.class);

        // Fetch the associated Question
        Question question = questionRepository.findById(userAnswerDTO.getQuestion().getId())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + userAnswerDTO.getQuestion().getId()));
        userAnswer.setQuestion(question);

        // Fetch the associated Answer
        Answer answer = answerRepository.findById(userAnswerDTO.getAnswer().getId())
                .orElseThrow(() -> new RuntimeException("Answer not found with id: " + userAnswerDTO.getAnswer().getId()));
        userAnswer.setAnswer(answer);

        // Fetch the associated UserAttempt
        UserAttempt userAttempt = userAttemptRepository.findById(userAnswerDTO.getUserAttempt().getId())
                .orElseThrow(() -> new RuntimeException("UserAttempt not found with id: " + userAnswerDTO.getUserAttempt().getId()));
        userAnswer.setUserAttempt(userAttempt);

        // Save the UserAnswer
        UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);

        // Add the UserAnswer to the UserAttempt's list of userAnswers
        userAttempt.getUserAnswers().add(savedUserAnswer);
        userAttemptRepository.save(userAttempt);

        // Map the saved UserAnswer back to DTO and return
        return modelMapper.map(savedUserAnswer, UserAnswerDTO.class);
    }

    // Read (Single UserAnswer)
    public UserAnswerDTO getUserAnswerById(Long id) {
        Optional<UserAnswer> userAnswer = userAnswerRepository.findById(id);
        return userAnswer.map(value -> modelMapper.map(value, UserAnswerDTO.class))
                .orElseThrow(() -> new RuntimeException("UserAnswer not found with id: " + id));
    }

    // Read (All UserAnswers)
    public List<UserAnswerDTO> getAllUserAnswers() {
        List<UserAnswer> userAnswers = userAnswerRepository.findAll();
        return userAnswers.stream()
                .map(userAnswer -> modelMapper.map(userAnswer, UserAnswerDTO.class))
                .collect(Collectors.toList());
    }

    // Update
    public UserAnswerDTO updateUserAnswer(Long id, UserAnswerDTO updatedUserAnswerDTO) {
        return userAnswerRepository.findById(id)
                .map(userAnswer -> {
                    // Fetch the associated Question
                    Question question = questionRepository.findById(updatedUserAnswerDTO.getQuestion().getId())
                            .orElseThrow(() -> new RuntimeException("Question not found with id: " + updatedUserAnswerDTO.getQuestion().getId()));
                    userAnswer.setQuestion(question);

                    // Fetch the associated Answer
                    Answer answer = answerRepository.findById(updatedUserAnswerDTO.getAnswer().getId())
                            .orElseThrow(() -> new RuntimeException("Answer not found with id: " + updatedUserAnswerDTO.getAnswer().getId()));
                    userAnswer.setAnswer(answer);

                    // Fetch the associated UserAttempt
                    UserAttempt userAttempt = userAttemptRepository.findById(updatedUserAnswerDTO.getUserAttempt().getId())
                            .orElseThrow(() -> new RuntimeException("UserAttempt not found with id: " + updatedUserAnswerDTO.getUserAttempt().getId()));
                    userAnswer.setUserAttempt(userAttempt);

                    // Save the updated UserAnswer
                    UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);

                    // Ensure the UserAnswer is in the UserAttempt's list of userAnswers
                    if (!userAttempt.getUserAnswers().contains(savedUserAnswer)) {
                        userAttempt.getUserAnswers().add(savedUserAnswer);
                        userAttemptRepository.save(userAttempt);
                    }

                    // Map the saved UserAnswer back to DTO and return
                    return modelMapper.map(savedUserAnswer, UserAnswerDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("UserAnswer not found with id: " + id));
    }

    // Delete
    public void deleteUserAnswer(Long id) {
        UserAnswer userAnswer = userAnswerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserAnswer not found with id: " + id));

        // Remove the UserAnswer from the associated UserAttempt's list of userAnswers
        UserAttempt userAttempt = userAnswer.getUserAttempt();
        userAttempt.getUserAnswers().remove(userAnswer);
        userAttemptRepository.save(userAttempt);

        // Delete the UserAnswer
        userAnswerRepository.delete(userAnswer);
    }
}
