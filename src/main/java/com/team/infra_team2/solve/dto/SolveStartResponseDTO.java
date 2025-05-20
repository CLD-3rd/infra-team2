package com.team.infra_team2.solve.dto;

public class SolveStartResponseDTO {
    private Long solveId;
    private String status; // IN_PROGRESS

    public SolveStartResponseDTO(Long solveId, String status) {
        this.solveId = solveId;
        this.status = status;
    }
}
