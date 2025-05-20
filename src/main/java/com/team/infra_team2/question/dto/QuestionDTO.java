package com.team.infra_team2.question.dto;

import com.team.infra_team2.user.dto.UserDTO;

import lombok.Getter;

@Getter
public class QuestionDTO {
	private Long questionId;
	private String questionText;
	private Integer correctAnswer;
	private UserDTO userDTO;

}
