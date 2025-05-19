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
        System.out.println("[DEBUG] getQuestionDetail 호출, questionId=" + questionId);

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> {
                    System.err.println("[ERROR] 문제를 찾을 수 없습니다: " + questionId);

                    // DB에 실제 존재하는 모든 question id 출력해보기
                    List<Question> allQuestions = questionRepository.findAll();
                    System.out.println("[DEBUG] 현재 DB에 존재하는 questionId 목록:");
                    for (Question q : allQuestions) {
                        System.out.println(" - questionId: " + q.getId());
                    }

                    return new EntityNotFoundException("문제를 찾을 수 없습니다: " + questionId);
                });

        List<Choice> choices = choiceRepository.findByQuestionIdOrderByChoiceNumber(questionId);

        return GetQuestionDetailResponseDTO.from(question, choices);
    }

}