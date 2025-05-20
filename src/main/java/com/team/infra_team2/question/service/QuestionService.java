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
}