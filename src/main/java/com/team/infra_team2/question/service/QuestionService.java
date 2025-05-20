package com.team.infra_team2.question.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
     
    /**
     * 문제 생성
     */
    @Transactional
    public QuestionCreateResponseDTO createQuestion(QuestionCreateRequestDTO requestDTO, User user) {
        //Question 엔티티 생성 및 값 주입
        Question question = new Question();
        question.setQuestionText(requestDTO.getQuestionText());
        question.setCorrectAnswer(requestDTO.getCorrectAnswer());
        question.setUser(user);
        
        Question savedQuestion = questionRepository.save(question);
        
        List<Choice> choices = new ArrayList<>();
        for(int i = 0; i < requestDTO.getChoicesCreate().size(); i++) {
            QuestionCreateRequestDTO.ChoiceCreateRequestDTO choiceDTO = requestDTO.getChoicesCreate().get(i);
            String choice_text = choiceDTO.getChoiceText();
            int choice_number = choiceDTO.getChoiceNumber();
            
            Choice choice = new Choice();
            choice.setChoiceText(choice_text);
            choice.setChoiceNumber(choice_number);
            choice.setQuestion(savedQuestion);
            choice.setIsCorrect(choice_number == requestDTO.getCorrectAnswer());
            
            choices.add(choice);
        }
        choiceRepository.saveAll(choices);
        return new QuestionCreateResponseDTO(savedQuestion.getId(), "문제가 등록되었습니다.");
    }
    
    /**
     * 문제 목록 조회
     */
    @Transactional(readOnly = true)
    public List<GetQuestionListResponseDTO> getQuestionList(int page, int size) {
        // 페이지는 0부터 시작하므로 1을 빼줌
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Question> questionPage = questionRepository.findAll(pageable);
        
        return questionPage.getContent().stream()
                .map(GetQuestionListResponseDTO::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 문제 상세 조회
     */
    @Transactional(readOnly = true)
    public GetQuestionDetailResponseDTO getQuestionDetail(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("해당 문제를 찾을 수 없습니다. ID: " + questionId));
        
        List<Choice> choices = choiceRepository.findByQuestionId(questionId);
        
        return GetQuestionDetailResponseDTO.from(question, choices);
    }
    
    /**
     * 전체 문제 개수 조회
     */
    @Transactional(readOnly = true)
    public long getTotalQuestionCount() {
        return questionRepository.count();
    }
    
    @Transactional(readOnly = true)
    public int getCurrentIndex(Long currentQuestionId) {
        // 모든 문제 ID를 오름차순으로 가져옴
        List<Long> idList = questionRepository.findAllIdsOrderByIdAsc();
        
        // 현재 문제 ID의 인덱스를 찾음
        int index = idList.indexOf(currentQuestionId);
        
        // 1-based index로 변환 (1부터 시작하는 인덱스)
        return index + 1;
    }
    
    public Long getNextQuestionId(Long currentQuestionId) {
        List<Long> idList = questionRepository.findAllIdsOrderByIdAsc(); // 오름차순
        int index = idList.indexOf(currentQuestionId);

        if (index == -1 || index + 1 >= idList.size()) {
            return null; // 삭제되었거나 마지막 문제
        }

        return idList.get(index + 1);
    }
    
    @Transactional
    public void updateQuestion(Long questionId, QuestionCreateRequestDTO dto) {
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new EntityNotFoundException("문제를 찾을 수 없습니다."));

        question.setQuestionText(dto.getQuestionText());
        question.setCorrectAnswer(dto.getCorrectAnswer());

        // 🔥 선택지 수정은 나중에 구현해도 됨
    }


}