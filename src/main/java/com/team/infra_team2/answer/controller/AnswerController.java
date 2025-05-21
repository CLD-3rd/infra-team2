package com.team.infra_team2.answer.controller;

import java.time.LocalDateTime;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team.infra_team2.answer.dto.request.AnswerSubmitDetailRequestDTO;
import com.team.infra_team2.answer.dto.response.AnswerSubmitDetailResponseDTO;
import com.team.infra_team2.answer.service.AnswerService;
import com.team.infra_team2.question.service.QuestionService;
import com.team.infra_team2.solve.constant.SolveStatus;
import com.team.infra_team2.solve.entity.Solve;
import com.team.infra_team2.solve.repository.SolveRepository;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/answers")
public class AnswerController {
    
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final SolveRepository solveRepository;  // 추가

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String submitAnswerDetail(
            @ModelAttribute AnswerSubmitDetailRequestDTO answerSubmitDetailRequest,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            HttpSession session) {
    	Long solveId = (Long) session.getAttribute("solveId");
        // 답안 저장
        AnswerSubmitDetailResponseDTO responseDTO =
                answerService.submitAnswerDetail(answerSubmitDetailRequest, solveId, principalDetails);

        // 다음 문제 ID 계산 (오름차순 리스트 기반)
        Long nextQuestionId = questionService.getNextQuestionId(answerSubmitDetailRequest.getQuestionId());
        System.out.println("nextQuestionId: " + nextQuestionId);
        
        // 문제 삭제되었거나 마지막 문제면 → 종료 페이지로 리다이렉트
        if (nextQuestionId == null) {
            Solve solve = solveRepository.findById(solveId)
                    .orElseThrow(() -> new IllegalArgumentException("Solve not found"));
            solve.setStatus(SolveStatus.FINISHED);
            solve.setFinishedAt(LocalDateTime.now());

            return "redirect:/api/history";
        }

        // 다음 문제로 이동
        return "redirect:/api/questions/" + nextQuestionId;
    }

    // 추가: 답안 제출 후 풀이 종료
    //endpoint에서 /{solveId}삭제
    @PostMapping("/finish")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public String finishAnswerAndSolve(
            @ModelAttribute AnswerSubmitDetailRequestDTO answerSubmitDetailRequest,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            HttpSession session) {
    	Long solveId = (Long) session.getAttribute("solveId");
        
        // 1. 답안 저장
        answerService.submitAnswerDetail(answerSubmitDetailRequest, solveId, principalDetails);
        
        // 2. solve 종료 처리
        Solve solve = solveRepository.findById(solveId)
                .orElseThrow(() -> new IllegalArgumentException("Solve not found"));
        solve.setStatus(SolveStatus.FINISHED);
        solve.setFinishedAt(LocalDateTime.now());
        
        // 3. 히스토리 페이지로 리다이렉트
        return "redirect:/api/history";
    }
}
