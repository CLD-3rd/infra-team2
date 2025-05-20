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
import com.team.infra_team2.solve.repository.SolveRepository;
import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.repository.UserRepository;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final UserRepository userRepository;
    private final SolveRepository solveRepository;

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
    @GetMapping("/api/questions/{questionId}")
    @PreAuthorize("isAuthenticated()")
    public String getQuestionDetail(
            @PathVariable(name = "questionId") Long questionId,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            HttpSession session,
            Model model) {
        GetQuestionDetailResponseDTO response = questionService.getQuestionDetail(questionId);
        model.addAttribute("question", response);
        
        Long solveId = (Long) session.getAttribute("solveId");

        
        User user = userRepository.findByUsername(principalDetails.getUsername());
        
        // 진행 상태 정보 추가
        model.addAttribute("currentIndex", questionService.getCurrentIndex(questionId));
        model.addAttribute("totalCount", questionService.getTotalQuestionCount());
        
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
    
 // 문제 수정 폼
    @GetMapping("/api/questions/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        GetQuestionDetailResponseDTO dto = questionService.getQuestionDetail(id);
        model.addAttribute("question", dto);
        return "question_edit_form"; // 이 템플릿으로 이동
    }

    // 문제 수정 제출 처리
    @PostMapping("/api/questions/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editQuestion(
    		@PathVariable("id") Long id,
            @ModelAttribute QuestionCreateRequestDTO requestDTO) {
        questionService.updateQuestion(id, requestDTO);
        return "redirect:/api/questions/" + id; // 수정 완료 후 상세페이지로 이동
    }
    
 // QuestionController.java

    @PostMapping("/api/questions/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/api/questions"; // 삭제 후 목록 페이지로 이동
    }


}
