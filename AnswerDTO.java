package com.team.infra_team2.answer.dto;

import lombok.Getter;

@Getter
public class AnswerDTO {
	private Long answer_id;
	private Integer selected_answer;
	private Boolean is_correct;
	private Long question_id;
	private Long solve_id;
	private Long user_id;

}
