package com.team.infra_team2.solve.entity;

import java.time.LocalDateTime;

import com.team.infra_team2.common.entity.BaseEntity;
import com.team.infra_team2.solve.constant.SolveStatus;
import com.team.infra_team2.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Entity
public class Solve extends BaseEntity {
	
	@Id
	@Column(name = "solve_id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private SolveStatus status;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "finished_at")
	private LocalDateTime finished_at;
}
