package com.team.infra_team2.question.controller;
import com.team.infra_team2.question.dto.GetQuestionDetailResponseDTO;
import com.team.infra_team2.question.dto.GetQuestionListResponseDTO;
import com.team.infra_team2.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 문제 관련 API 엔드포인트를 처리하는 컨트롤러
 * 문제 목록 조회 및 문제 상세 조회 기능을 제공함
 */
@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
// 아직 로그인 인증 관련 기능이 탑재되지 않음.
// 기본적인 로직만 작성해둔 상황임
public class QuestionController {
    
    private final QuestionService questionService;
    
    /**
     * 문제 목록을 페이징하여 조회하는 API
     * GET /api/questions?page=1&size=10
     * 
     * @param page 페이지 번호 (기본값: 1)
     * @param size 페이지당 항목 수 (기본값: 10)
     * @return 페이징된 문제 목록과 200 OK 응답
     */
    @GetMapping
    // 한 페이지에 기본적으로 10개씩 띄우는 것으로 설정
    public ResponseEntity<List<GetQuestionListResponseDTO>> getQuestionList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<GetQuestionListResponseDTO> responseList = questionService.getQuestionList(page, size);
        return ResponseEntity.ok(responseList);
    }
    
    /**
     * 단일 문제의 상세 정보를 조회하는 API
     * GET /api/questions/{questionId}
     * 
     * @param questionId 조회할 문제의 ID
     * @return 문제 상세 정보(선택지 포함)와 200 OK 응답
     */
    @GetMapping("/{questionId}")
    // 한 개의 문제를 클릭한 경우 해당 문제의 세부정보를 반환
    public ResponseEntity<GetQuestionDetailResponseDTO> getQuestionDetail(
            @PathVariable Long questionId) {
        
        GetQuestionDetailResponseDTO response = questionService.getQuestionDetail(questionId);
        return ResponseEntity.ok(response);
    }
    
    // TODO: 문제 풀이 시작(solve 생성) API 구현 필요
    // TODO: 이전/다음 문제 조회 기능 구현 필요
}