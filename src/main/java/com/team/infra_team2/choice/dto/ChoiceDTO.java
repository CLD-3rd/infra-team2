package com.team.infra_team2.choice.dto;

import com.team.infra_team2.question.dto.QuestionDTO;

import lombok.Getter;

@Getter
public class ChoiceDTO {
	private Long choiceId;
	private String choiceText;	
    private Integer choiceNumber;
	private Boolean isCorrect;
	private QuestionDTO questionDTO;
   
}
