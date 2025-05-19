package com.team.infra_team2.solve.constant;

import lombok.Getter;

@Getter
public enum SolveStatus {
    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED"),
    FORCE_TERMINATED("FORCE_TERMINATED");
    
    
	private String status;

	SolveStatus(String status) {
		this.status = status;
	}
}
