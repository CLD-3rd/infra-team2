package com.team.infra_team2.choice.entity;

import com.team.infra_team2.common.entity.BaseEntity;
import com.team.infra_team2.question.entity.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Entity
@Setter
@NoArgsConstructor
public class Choice extends BaseEntity {
	
	@Id
	@Column(name = "choice_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "choice_text")
	private String choice_text;
	
	@Column(name = "is_correct")
	private Boolean is_correct;
	
	@Column(name = "choice_number")
	private Integer choice_number;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;
}
