package com.team.infra_team2.answer.entity;

import com.team.infra_team2.common.entity.BaseEntity;
import com.team.infra_team2.question.entity.Question;
import com.team.infra_team2.solve.entity.Solve;
import com.team.infra_team2.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Answer extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id")
	private Long id;
	
	@Column(name = "selected_answer")
	private Integer selectedAnswer;
	
	@Column(name = "is_correct")
	private Boolean isCorrect;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "solve_id")
	private Solve solve;
	
	private Answer(Integer selectedAnswer, Boolean isCorrect, User user, Question question, Solve solve) {
		this.selectedAnswer = selectedAnswer;
		this.isCorrect = isCorrect;
		this.user = user;
		this.question = question;
		this.solve = solve;
	}
	
	public static Answer of(Integer selected_answer, Boolean is_correct, User user, Question question,
			Solve solve) {
		return new Answer(selected_answer, is_correct, user, question, solve);
	}
}
