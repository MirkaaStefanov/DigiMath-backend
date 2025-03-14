package com.example.DigiMath_backend.services;

import com.example.DigiMath_backend.dtos.AnswerDTO;
import com.example.DigiMath_backend.models.Answer;
import com.example.DigiMath_backend.models.Question;
import com.example.DigiMath_backend.repositories.AnswerRepository;
import com.example.DigiMath_backend.repositories.QuestionRepository;
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
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public AnswerDTO save(AnswerDTO answerDTO) {
        Answer answer = modelMapper.map(answerDTO, Answer.class);
        return modelMapper.map(answerRepository.save(answer), AnswerDTO.class);
    }

    public AnswerDTO findById(Long id) throws ChangeSetPersister.NotFoundException {
        Answer answer = answerRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return modelMapper.map(answer, AnswerDTO.class);
    }
}
