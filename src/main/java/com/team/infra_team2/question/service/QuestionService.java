package com.team.infra_team2.question.service;

import com.team.infra_team2.choice.entity.Choice;
import com.team.infra_team2.choice.repository.ChoiceRepository;
import com.team.infra_team2.question.dto.GetQuestionDetailResponseDTO;
import com.team.infra_team2.question.dto.GetQuestionListResponseDTO;
import com.team.infra_team2.question.dto.QuestionCreateRequestDTO;
import com.team.infra_team2.question.dto.QuestionCreateResponseDTO;
import com.team.infra_team2.question.entity.Question;
import com.team.infra_team2.question.repository.QuestionRepository;
import com.team.infra_team2.user.entity.User;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    /**
     * 문제 목록을 페이징하여 조회
     */
    @Transactional(readOnly = true)
    public List<GetQuestionListResponseDTO> getQuestionList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Question> questions = questionRepository.findAllByOrderByIdDesc(pageable);

        return questions.getContent().stream()
                .map(GetQuestionListResponseDTO::from)
                .collect(Collectors.toList());
    }

    /**
     * 문제 상세 정보 조회
     */
    @Transactional(readOnly = true)
    public GetQuestionDetailResponseDTO getQuestionDetail(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("문제를 찾을 수 없습니다: " + questionId));

        List<Choice> choices = choiceRepository.findByQuestionIdOrderByChoiceNumber(questionId);

        return GetQuestionDetailResponseDTO.from(question, choices);
    }

    /**
     * 문제 등록
     */
    @Transactional
    public QuestionCreateResponseDTO createQuestion(QuestionCreateRequestDTO requestDTO, User user) {
        Question question = new Question();
        question.setQuestionText(requestDTO.getQuestionText());
        question.setCorrectAnswer(requestDTO.getCorrectAnswer());
        question.setUser(user);

        Question savedQuestion = questionRepository.save(question);

        List<Choice> choices = new ArrayList<>();
        for (QuestionCreateRequestDTO.ChoiceCreateRequestDTO choiceDTO : requestDTO.getChoicesCreate()) {
            Choice choice = new Choice();
            choice.setChoiceText(choiceDTO.getChoiceText());
            choice.setChoiceNumber(choiceDTO.getChoiceNumber());
            choice.setIsCorrect(choiceDTO.getChoiceNumber() == requestDTO.getCorrectAnswer());
            choice.setQuestion(savedQuestion);
            choices.add(choice);
        }

        choiceRepository.saveAll(choices);
        return new QuestionCreateResponseDTO(savedQuestion.getId(), "문제가 등록되었습니다.");
    }
    
    @Transactional(readOnly = true)
    public long getTotalQuestionCount() {
        return questionRepository.count();
    }
}
