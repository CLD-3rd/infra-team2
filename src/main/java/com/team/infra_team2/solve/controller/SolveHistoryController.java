package com.team.infra_team2.solve.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team.infra_team2.solve.dto.SolveHistoryListResponseDTO;
import com.team.infra_team2.solve.service.SolveHistoryService;
import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.repository.UserRepository;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class SolveHistoryController {
	private final SolveHistoryService solveHistoryService;
	private final UserRepository userRepository;
	
	@GetMapping("/history")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String getHistory(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Model model
    ) {
		// 로그인 된 사용자 정보 꺼내기
		String username = principalDetails.getUsername();
		
		// username으로 User 엔티티 조회
		User user = userRepository.findByUsername(username);
		if (user == null) {
	    	    throw new RuntimeException("유저가 없습니다");
	    	}
		SolveHistoryListResponseDTO dto = solveHistoryService.getSolveHistoryByUser(user);

		model.addAttribute("solveHistoryList", dto.getHistoryList());
		
        return "solve_result";
    }
	
}
