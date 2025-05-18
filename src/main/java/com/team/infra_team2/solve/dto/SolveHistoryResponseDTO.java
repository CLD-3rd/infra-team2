package com.team.infra_team2.solve.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SolveHistoryResponseDTO {
	
	 // solve 고유 ID
    private Long solve_id;

    // 풀이 종료 시각 
    private LocalDateTime solved_at;

    // 총 풀이 문항 수
    private int total_question;

    // 맞힌 문항 수
    private int correct_count;
	
}
