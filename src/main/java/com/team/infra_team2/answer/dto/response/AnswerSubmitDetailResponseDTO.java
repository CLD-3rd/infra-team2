package com.team.infra_team2.answer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnswerSubmitDetailResponseDTO {
	
	private Long answerId;
	private String message;
	
	public static AnswerSubmitDetailResponseDTO of(Long id, String message) {
		return new AnswerSubmitDetailResponseDTO(id, message);
	}

}
