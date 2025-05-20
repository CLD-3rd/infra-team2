package com.team.infra_team2.solve.controller;

import java.time.LocalDateTime;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team.infra_team2.solve.constant.SolveStatus;
import com.team.infra_team2.solve.entity.Solve;
import com.team.infra_team2.solve.repository.SolveRepository;
import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.repository.UserRepository;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/questions")
public class SolveController {

    private final SolveRepository solveRepository;
    private final UserRepository userRepository;

    @PostMapping("/start")
    @Transactional
    public String startSolve(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam("questionId") Long questionId,
            Model model
    ) {
        String username = principalDetails.getUsername();
        User user = userRepository.findByUsername(username);

        // 기존 진행 중인 solve 종료
        solveRepository.findByUserAndStatus(user, SolveStatus.IN_PROGRESS)
                .forEach(s -> {
                    s.setStatus(SolveStatus.FINISHED);
                    s.setFinishedAt(LocalDateTime.now());
                });

        // 새 solve 생성
        Solve newSolve = Solve.create(user);
        solveRepository.save(newSolve);
        model.addAttribute("currentSolveId", newSolve.getId());

        // 문제 상세 페이지로 이동
        return "redirect:/api/questions/" + questionId + "?solveId=" + newSolve.getId();
    }
    
    @PostMapping("/finish")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public String finishSolve(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        String username = principalDetails.getUsername();
        User user = userRepository.findByUsername(username);

        // 현재 진행중인 solve를 찾아 FINISHED 상태로 변경
        solveRepository.findByUserAndStatus(user, SolveStatus.IN_PROGRESS)
                .forEach(s -> {
                    s.setStatus(SolveStatus.FINISHED);
                    s.setFinishedAt(LocalDateTime.now());
                });

        // 히스토리 페이지로 리다이렉트
        return "redirect:/api/history";
    }

}  