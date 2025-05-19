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

/**
 * 문제 관련 비즈니스 로직을 처리하는 서비스 클래스
 * 문제 조회 및 상세 정보 제공 기능을 담당함
 */
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    
    /**
     * 문제 목록을 페이징하여 조회
     * ID 기준 내림차순으로 정렬된 결과를 반환함
     * 
     * @param page 페이지 번호 (1부터 시작)
     * @param size 페이지 크기
     * @return 문제 목록 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<GetQuestionListResponseDTO> getQuestionList(int page, int size) {
        // 페이지는 0부터 시작하므로 1을 빼서 변환
        Pageable pageable = PageRequest.of(page - 1, size);
        
        // ID 내림차순으로 문제 목록 조회
        Page<Question> questions = questionRepository.findAllByOrderByIdDesc(pageable);
        
        // 엔티티를 DTO로 변환하여 반환
        return questions.getContent().stream()
                .map(GetQuestionListResponseDTO::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 특정 ID의 문제와 해당 문제의 선택지 목록을 조회
     * 
     * @param questionId 조회할 문제 ID
     * @return 문제 상세 정보 DTO (선택지 포함)
     * @throws EntityNotFoundException 문제를 찾을 수 없는 경우
     */
    @Transactional(readOnly = true)
    public GetQuestionDetailResponseDTO getQuestionDetail(Long questionId) {
        // 문제 ID로 문제 조회, 없으면 예외 발생
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("문제를 찾을 수 없습니다: " + questionId));
        
        // 문제에 속한 선택지 목록 조회
        List<Choice> choices = choiceRepository.findByQuestionIdOrderByChoiceNumber(questionId);
        
        // 문제와 선택지 정보를 DTO로 변환하여 반환
        return GetQuestionDetailResponseDTO.from(question, choices);
    }
    
    // TODO: "{id} 값에서 1을 뺀 값으로 다음 문제 조회" 기능 구현 필요
    // TODO: "풀이 시작 solve 생성" 기능 구현 필요
    // TODO: "이미 solve가 존재하면 초기화 여부 질문" 기능 구현 필요
}