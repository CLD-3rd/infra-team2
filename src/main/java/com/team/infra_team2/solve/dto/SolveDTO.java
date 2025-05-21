package com.team.infra_team2.solve.dto;

import java.time.LocalDateTime;

import com.team.infra_team2.user.dto.UserDTO;

import lombok.Getter;

@Getter
public class SolveDTO {
	private Long solveId;
	private String status;
	private LocalDateTime finishiedAt;
	private UserDTO userDTO;
}
