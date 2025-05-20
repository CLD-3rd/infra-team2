package com.team.infra_team2.answer.dto;

import com.team.infra_team2.answer.entity.Answer;
import com.team.infra_team2.question.dto.QuestionDTO;
import com.team.infra_team2.question.entity.Question;
import com.team.infra_team2.solve.dto.SolveDTO;
import com.team.infra_team2.solve.entity.Solve;
import com.team.infra_team2.user.dto.UserDTO;
import com.team.infra_team2.user.entity.User;

import lombok.Getter;

@Getter
public class AnswerDTO {
	private Long answer_id;
	private Integer selected_answer;
	private Boolean is_correct;
	private UserDTO userDTO;
	private QuestionDTO questionDTO;
	private SolveDTO solveDTO;
	
	public Answer toEntity(User user, Question question, Solve solve) {
		return Answer.of(selected_answer, is_correct, user, question, solve);
	}
}
