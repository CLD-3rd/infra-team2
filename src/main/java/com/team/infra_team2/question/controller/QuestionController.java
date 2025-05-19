package com.team.infra_team2.question.controller;

import com.team.infra_team2.question.dto.GetQuestionDetailResponseDTO;
import com.team.infra_team2.question.dto.GetQuestionListResponseDTO;
import com.team.infra_team2.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    
    private final QuestionService questionService;
    
    @GetMapping
    public ResponseEntity<List<GetQuestionListResponseDTO>> getQuestionList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<GetQuestionListResponseDTO> responseList = questionService.getQuestionList(page, size);
        return ResponseEntity.ok(responseList);
    }
    
    @GetMapping("/{questionId}")
    public ResponseEntity<GetQuestionDetailResponseDTO> getQuestionDetail(
            @PathVariable Long questionId) {
        
        GetQuestionDetailResponseDTO response = questionService.getQuestionDetail(questionId);
        return ResponseEntity.ok(response);
    }
}
