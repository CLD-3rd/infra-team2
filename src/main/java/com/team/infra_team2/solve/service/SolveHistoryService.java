package com.team.infra_team2.solve.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.team.infra_team2.answer.repository.AnswerRepository;
import com.team.infra_team2.solve.constant.SolveStatus;
import com.team.infra_team2.solve.dto.SolveHistoryDetailResponseDTO;
import com.team.infra_team2.solve.dto.SolveHistoryListResponseDTO;
import com.team.infra_team2.solve.entity.Solve;
import com.team.infra_team2.solve.repository.SolveRepository;
import com.team.infra_team2.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SolveHistoryService {
	private final SolveRepository solveRepository;
	private final AnswerRepository answerRepository;
	
	public SolveHistoryListResponseDTO getSolveHistoryByUser(User user) {
		List<Solve> solveList = solveRepository.findByUserAndStatus(user, SolveStatus.FINISHED);
		List<SolveHistoryDetailResponseDTO> dtoList = new ArrayList<>();
		
		for(Solve solve : solveList) {
			// 정답 갯수
			int correct_count = answerRepository.countBySolveAndIsCorrectTrue(solve);
			// 전체 문항 수
			int total_count = answerRepository.countBySolve(solve);
			
			SolveHistoryDetailResponseDTO dto =
		            SolveHistoryDetailResponseDTO.from(solve, correct_count, total_count);

//			SolveHistoryDetailResponseDTO dto = new SolveHistoryDetailResponseDTO();
//			dto.setSolve_id(solve.getId());
//			dto.setSolved_at(solve.getFinished_at());
//			dto.setCorrect_count(correct_count);
//			dto.setTotal_question(total_count);
			
			dtoList.add(dto);
		}
		SolveHistoryListResponseDTO response = new SolveHistoryListResponseDTO();
		response.setHistoryList(dtoList);

	    return response;

	}
}
