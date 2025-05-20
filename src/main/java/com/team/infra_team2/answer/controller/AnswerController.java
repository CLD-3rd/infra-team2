package com.team.infra_team2.answer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team.infra_team2.answer.dto.request.AnswerSubmitDetailRequestDTO;
import com.team.infra_team2.answer.dto.response.AnswerSubmitDetailResponseDTO;
import com.team.infra_team2.answer.service.AnswerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/answers")
public class AnswerController {
	
	private final AnswerService answerService;
	
	@PostMapping("/{solveId}")
	public ResponseEntity<AnswerSubmitDetailResponseDTO> submitAnswerDetail(
			@ModelAttribute AnswerSubmitDetailRequestDTO answerSubmitDetailRequest,
			@PathVariable("solveId") Long solveId) {
		AnswerSubmitDetailResponseDTO answerSubmitDetailResponseDTO = answerService.submitAnswerDetail(answerSubmitDetailRequest, solveId);
		
		return ResponseEntity.ok(answerSubmitDetailResponseDTO);
	}
}
