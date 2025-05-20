package com.team.infra_team2.solve.dto;

import java.time.LocalDateTime;

import com.team.infra_team2.solve.entity.Solve;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SolveHistoryDetailResponseDTO {
	
	 // solve 고유 ID
    private Long solveId;

    // 풀이 종료 시각 
    private LocalDateTime solvedAt;

    // 총 풀이 문항 수
    private int totalQuestion;

    // 맞힌 문항 수
    private int correctCount;
	
    public static SolveHistoryDetailResponseDTO from(Solve solve, int correctCount, int totalQuestion) {
        SolveHistoryDetailResponseDTO dto = new SolveHistoryDetailResponseDTO();
        dto.solveId = solve.getId();
        dto.solvedAt = solve.getFinishedAt();
        dto.correctCount = correctCount;
        dto.totalQuestion = totalQuestion;
        return dto;
    }

}
