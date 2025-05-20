package com.team.infra_team2.question.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team.infra_team2.question.dto.GetQuestionDetailResponseDTO;
import com.team.infra_team2.question.dto.GetQuestionListResponseDTO;
import com.team.infra_team2.question.dto.QuestionCreateRequestDTO;
import com.team.infra_team2.question.service.QuestionService;
import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.repository.UserRepository;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final UserRepository userRepository;

    // 문제 목록 페이지 (URL: /api/questions)
    @GetMapping("/api/questions")
    @PreAuthorize("isAuthenticated()")
    public String getQuestionList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {

        List<GetQuestionListResponseDTO> responseList = questionService.getQuestionList(page, size);
        long totalCount = questionService.getTotalQuestionCount(); // 이건 서비스에 구현되어 있어야 함
        int totalPages = (int) Math.ceil((double) totalCount / size);

        model.addAttribute("questions", responseList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "question_list";
    }

    // 문제 상세 페이지
    @GetMapping("/questions/{questionId}")
    @PreAuthorize("isAuthenticated()")
    public String getQuestionDetail(
            @PathVariable(name = "questionId") Long questionId, 
            Model model) {
        GetQuestionDetailResponseDTO response = questionService.getQuestionDetail(questionId);
        model.addAttribute("question", response);
        return "question_detail";
    }

    // 문제 등록 폼 (ADMIN만 접근 가능)
    @GetMapping("/api/questions/form")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreateForm(Model model) {
        model.addAttribute("question", new QuestionCreateRequestDTO());
        return "question_form";
    }

    // 문제 등록 처리 (ADMIN만 접근 가능)
    @PostMapping("/api/questions")
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
        return "redirect:/api/questions";
    }
}
