package com.team.infra_team2.question.controller;
import com.team.infra_team2.question.service.QuestionService;
import com.team.infra_team2.question.dto.GetQuestionDetailResponseDTO;
import com.team.infra_team2.question.dto.GetQuestionListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor


// 아직 로그인 인증 관련 기능이 탑재되지 않음.
// 기본적인 로직만 작성해좋은 상황임
public class QuestionController {
    
    private final QuestionService questionService;
    
    @GetMapping
    // 한 페이지에 기본적으로 10개씩 띄우는 것으로 설정
    public ResponseEntity<List<GetQuestionListResponseDTO>> getQuestionList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<GetQuestionListResponseDTO> responseList = questionService.getQuestionList(page, size);
        return ResponseEntity.ok(responseList);
    }
    
    @GetMapping("/{questionId}")
    // 한 개의 문제를 클릭한 경우 해당 문제의 세부정보를 반환
    public ResponseEntity<GetQuestionDetailResponseDTO> getQuestionDetail(
            @PathVariable Long questionId) {
        
        GetQuestionDetailResponseDTO response = questionService.getQuestionDetail(questionId);
        return ResponseEntity.ok(response);
    }
}
