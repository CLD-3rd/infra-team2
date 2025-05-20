package com.team.infra_team2.solve.dto;

import java.time.LocalDateTime;

import com.team.infra_team2.user.dto.UserDTO;

import lombok.Getter;

@Getter
public class SolveDTO {
	private Long solve_id;
	private String status;
	private LocalDateTime finishied_at;
	private UserDTO userDTO;
}
