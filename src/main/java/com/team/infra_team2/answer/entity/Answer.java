package com.team.infra_team2.answer.entity;

import com.team.infra_team2.common.entity.BaseEntity;
import com.team.infra_team2.question.entity.Question;
import com.team.infra_team2.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Entity
public class Answer extends BaseEntity {
	
	@Id
	@Column(name = "answer_id")
	private Long id;
	
	@Column(name = "selected_answer")
	private Integer selected_answer;
	
	@Column(name = "is_correct")
	private Boolean is_correct;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "solve_id")
	private Solve solve;
}
