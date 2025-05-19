package com.team.infra_team2.solve.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team.infra_team2.solve.dto.SolveHistoryListResponseDTO;
import com.team.infra_team2.solve.service.SolveHistoryService;
import com.team.infra_team2.user.constant.UserRoleType;
import com.team.infra_team2.user.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SolveHistoryController {
	private final SolveHistoryService solveHistoryService;
	@GetMapping("/history")
    public SolveHistoryListResponseDTO getHistory() {
        // 아직 인증 처리 안 됐으니까 임시 유저 객체 생성
        User dummyUser = User.of("dummy_user", "encoded_pw", UserRoleType.USER);
        dummyUser.setUser_id(1L);

        return solveHistoryService.getSolveHistoryByUser(dummyUser);
    }
}
