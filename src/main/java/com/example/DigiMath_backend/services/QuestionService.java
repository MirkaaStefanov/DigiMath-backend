package com.example.DigiMath_backend.services;

import com.example.DigiMath_backend.dtos.QuestionDTO;
import com.example.DigiMath_backend.models.Question;
import com.example.DigiMath_backend.models.Test;
import com.example.DigiMath_backend.repositories.QuestionRepository;
import com.example.DigiMath_backend.repositories.TestRepository;
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
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        Question question = modelMapper.map(questionDTO, Question.class);

        Test test = testRepository.findById(questionDTO.getTest().getId())
                .orElseThrow(() -> new RuntimeException("Test not found with id: " + questionDTO.getTest().getId()));

        question.setTest(test);

        Question savedQuestion = questionRepository.save(question);

        test.getQuestions().add(savedQuestion);
        testRepository.save(test);

        return modelMapper.map(savedQuestion, QuestionDTO.class);
    }

    public QuestionDTO getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.map(value -> modelMapper.map(value, QuestionDTO.class))
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }

    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionDTO.class))
                .collect(Collectors.toList());
    }

    public QuestionDTO updateQuestion(Long id, QuestionDTO updatedQuestionDTO) {
        return questionRepository.findById(id)
                .map(question -> {
                    // Fetch the associated Test
                    Test test = testRepository.findById(updatedQuestionDTO.getTest().getId())
                            .orElseThrow(() -> new RuntimeException("Test not found with id: " + updatedQuestionDTO.getTest().getId()));

                    // Update the Question fields
                    modelMapper.map(updatedQuestionDTO, question);
                    question.setTest(test);

                    // Save the updated Question
                    Question savedQuestion = questionRepository.save(question);

                    // Ensure the Question is in the Test's list of questions
                    if (!test.getQuestions().contains(savedQuestion)) {
                        test.getQuestions().add(savedQuestion);
                        testRepository.save(test);
                    }

                    // Map the saved Question back to DTO and return
                    return modelMapper.map(savedQuestion, QuestionDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }

    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        Test test = question.getTest();
        test.getQuestions().remove(question);
        testRepository.save(test);

        questionRepository.delete(question);
    }
}
