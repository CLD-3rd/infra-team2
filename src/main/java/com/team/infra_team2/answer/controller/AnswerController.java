package com.team.infra_team2.answer.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team.infra_team2.answer.dto.request.AnswerSubmitDetailRequestDTO;
import com.team.infra_team2.answer.dto.response.AnswerSubmitDetailResponseDTO;
import com.team.infra_team2.answer.service.AnswerService;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/answers")
public class AnswerController {
	
	private final AnswerService answerService;
	
	
	@PostMapping("/{solveId}")
	@PreAuthorize("hasRole('USER')")
	public String submitAnswerDetail(
			@ModelAttribute AnswerSubmitDetailRequestDTO answerSubmitDetailRequest,
			@PathVariable("solveId") Long solveId,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {

		AnswerSubmitDetailResponseDTO answerSubmitDetailResponseDTO = answerService.submitAnswerDetail(answerSubmitDetailRequest, solveId, principalDetails);
		
		
		return "redirect:/api/questions/" + (answerSubmitDetailRequest.getQuestionId() - 1);
	}
}
