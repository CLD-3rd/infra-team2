package com.team.infra_team2.answer.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerSubmitDetailRequestDTO {
	
	private Long questionId;
	private Integer selectedAnswer;
	
}
