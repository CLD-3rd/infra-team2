package com.team.infra_team2.question.controller;

import com.team.infra_team2.question.dto.*;
import com.team.infra_team2.question.service.QuestionService;
import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.repository.UserRepository;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // @Controller 사용 - 뷰와 API를 모두 처리
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final UserRepository userRepository;

    // ========== REST API 엔드포인트 ==========
    
    @GetMapping("/api/questions")
    @ResponseBody // API로 JSON 응답을 반환하기 위해 필요
    public ResponseEntity<List<GetQuestionListResponseDTO>> getQuestionListApi(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        List<GetQuestionListResponseDTO> responseList = questionService.getQuestionList(page, size);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/api/questions/{questionId}")
    @ResponseBody
    public ResponseEntity<GetQuestionDetailResponseDTO> getQuestionDetailApi(
            @PathVariable(name = "questionId") Long questionId) {
        GetQuestionDetailResponseDTO response = questionService.getQuestionDetail(questionId);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/api/questions")
    @ResponseBody // API로 JSON 응답을 반환하기 위해 필요
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuestionCreateResponseDTO> createQuestionApi(
            @RequestBody QuestionCreateRequestDTO requestDTO,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String username = principalDetails.getUsername();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("유저가 없습니다");
        }
        QuestionCreateResponseDTO responseDTO = questionService.createQuestion(requestDTO, user);
        return ResponseEntity.ok(responseDTO);
    }

    // ========== 타임리프 뷰 엔드포인트 ==========
    
    @GetMapping("/questions")
    public String getQuestionList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {
        List<GetQuestionListResponseDTO> responseList = questionService.getQuestionList(page, size);
        
        // 총 페이지 수 계산 로직 추가
        long totalCount = questionService.getTotalQuestionCount(); // 이 메서드는 구현해야 합니다.
        int totalPages = (int) Math.ceil((double) totalCount / size);
        
        model.addAttribute("questions", responseList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "question_list";
    }

    @GetMapping("/questions/{questionId}")
    public String getQuestionDetail(
            @PathVariable(name = "questionId") Long questionId, 
            Model model) {
        GetQuestionDetailResponseDTO response = questionService.getQuestionDetail(questionId);
        model.addAttribute("question", response);
        return "question_detail";
    }

    @GetMapping("/questions/form")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreateForm(Model model) {
        model.addAttribute("question", new QuestionCreateRequestDTO());
        return "question_form"; // resources/templates/question_form.html
    }

    @PostMapping("/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public String createQuestion(
            @ModelAttribute QuestionCreateRequestDTO requestDTO,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String username = principalDetails.getUsername();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("유저가 없습니다");
        }
        questionService.createQuestion(requestDTO, user);
        return "redirect:/questions"; // 등록 후 목록 페이지로 리다이렉트
    }
}