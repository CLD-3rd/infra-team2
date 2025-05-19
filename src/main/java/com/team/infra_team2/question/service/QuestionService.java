package com.team.infra_team2.question.service;

import com.team.infra_team2.choice.entity.Choice;
import com.team.infra_team2.choice.repository.ChoiceRepository;
import com.team.infra_team2.question.dto.GetQuestionDetailResponseDTO;
import com.team.infra_team2.question.dto.GetQuestionListResponseDTO;
import com.team.infra_team2.question.entity.Question;
import com.team.infra_team2.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    /**
     * 문제 목록 조회 (페이징)
     * @param page 페이지 번호 (1부터 시작)
     * @param size 페이지 크기
     * @return 문제 목록 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<GetQuestionListResponseDTO> getQuestionList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        
        Page<Question> questions = questionRepository.findAllByOrderByIdDesc(pageable);
        
        return questions.getContent().stream()
                .map(GetQuestionListResponseDTO::from)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public GetQuestionDetailResponseDTO getQuestionDetail(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("문제를 찾을 수 없습니다: " + questionId));
        
        List<Choice> choices = choiceRepository.findByQuestionIdOrderByChoiceNumber(questionId);
        
        return GetQuestionDetailResponseDTO.from(question, choices);
    }
    
    
}