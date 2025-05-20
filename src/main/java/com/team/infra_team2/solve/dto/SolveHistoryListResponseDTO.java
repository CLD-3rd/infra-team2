package com.team.infra_team2.solve.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SolveHistoryListResponseDTO {
	
	 private List<SolveHistoryDetailResponseDTO> historyList;
	
}
