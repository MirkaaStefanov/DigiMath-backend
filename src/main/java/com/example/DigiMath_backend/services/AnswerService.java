package com.example.DigiMath_backend.services;

import com.example.DigiMath_backend.dtos.AnswerDTO;
import com.example.DigiMath_backend.models.Answer;
import com.example.DigiMath_backend.models.Question;
import com.example.DigiMath_backend.repositories.AnswerRepository;
import com.example.DigiMath_backend.repositories.QuestionRepository;
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
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public List<Answer> createAnswersInQuestion(List<AnswerDTO> answerDTOS, Question question){

        List<Answer> answers = answerDTOS.stream().map(value -> modelMapper.map(value, Answer.class)).collect(Collectors.toList());;

        for (Answer answer : answers){
            answer.setQuestion(question);
        }

        return answerRepository.saveAll(answers);
    }

    public AnswerDTO createAnswer(AnswerDTO answerDTO) {
        Answer answer = modelMapper.map(answerDTO, Answer.class);
        Question question = questionRepository.findById(answerDTO.getQuestion())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + answerDTO.getQuestion()));

        answer.setQuestion(question);
        Answer savedAnswer = answerRepository.save(answer);
        question.getAnswers().add(savedAnswer);
        questionRepository.save(question);

        // Map the saved Answer back to DTO and return
        return modelMapper.map(savedAnswer, AnswerDTO.class);
    }

    // Read (Single Answer)
    public AnswerDTO getAnswerById(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        return answer.map(value -> modelMapper.map(value, AnswerDTO.class))
                .orElseThrow(() -> new RuntimeException("Answer not found with id: " + id));
    }

    // Read (All Answers)
    public List<AnswerDTO> getAllAnswers() {
        List<Answer> answers = answerRepository.findAll();
        return answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .collect(Collectors.toList());
    }

    // Update
    public AnswerDTO updateAnswer(Long id, AnswerDTO updatedAnswerDTO) {
        return answerRepository.findById(id)
                .map(answer -> {
                    // Fetch the associated Question
                    Question question = questionRepository.findById(updatedAnswerDTO.getQuestion())
                            .orElseThrow(() -> new RuntimeException("Question not found with id: " + updatedAnswerDTO.getQuestion()));

                    // Update the Answer fields
                    modelMapper.map(updatedAnswerDTO, answer);
                    answer.setQuestion(question);

                    // Save the updated Answer
                    Answer savedAnswer = answerRepository.save(answer);

                    // Ensure the Answer is in the Question's list of answers
                    if (!question.getAnswers().contains(savedAnswer)) {
                        question.getAnswers().add(savedAnswer);
                        questionRepository.save(question);
                    }

                    // Map the saved Answer back to DTO and return
                    return modelMapper.map(savedAnswer, AnswerDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Answer not found with id: " + id));
    }

    // Delete
    public void deleteAnswer(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found with id: " + id));

        // Remove the Answer from the associated Question's list of answers
        Question question = answer.getQuestion();
        question.getAnswers().remove(answer);
        questionRepository.save(question);

        // Delete the Answer
        answerRepository.delete(answer);
    }
}
