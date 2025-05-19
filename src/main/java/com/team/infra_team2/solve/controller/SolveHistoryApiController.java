package com.team.infra_team2.solve.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team.infra_team2.solve.dto.SolveHistoryListResponseDTO;
import com.team.infra_team2.solve.service.SolveHistoryService;
import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.repository.UserRepository;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class SolveHistoryApiController {

    private final SolveHistoryService solveHistoryService;
    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SolveHistoryListResponseDTO> getHistoryJson(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        String username = principalDetails.getUsername();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("유저가 없습니다");
        }

        SolveHistoryListResponseDTO dto = solveHistoryService.getSolveHistoryByUser(user);
        return ResponseEntity.ok(dto);
    }
}

