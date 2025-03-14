package com.example.DigiMath_backend.services;

import com.example.DigiMath_backend.dtos.QuestionDTO;
import com.example.DigiMath_backend.models.Answer;
import com.example.DigiMath_backend.models.Question;
import com.example.DigiMath_backend.models.Test;
import com.example.DigiMath_backend.repositories.QuestionRepository;
import com.example.DigiMath_backend.repositories.TestRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    private final AnswerService answerService;
    private final ModelMapper modelMapper;

    public QuestionDTO findById(Long id) throws ChangeSetPersister.NotFoundException {
        Question question = questionRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return modelMapper.map(question, QuestionDTO.class);
    }

    public QuestionDTO save(QuestionDTO questionDTO) {
        Question question = modelMapper.map(questionDTO, Question.class);
        return modelMapper.map(questionRepository.save(question), QuestionDTO.class);
    }
}
