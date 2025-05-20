package com.team.infra_team2.solve.controller;

import java.time.LocalDateTime;

import com.team.infra_team2.solve.constant.SolveStatus;
import com.team.infra_team2.solve.dto.SolveStartResponseDTO;
import com.team.infra_team2.solve.entity.Solve;

public class SolveStartService {

	@Transactional
	public SolveStartResponseDTO startSolve(User user) {
	    // 1. 기존 진행 중인 solve 있는지 확인
	    Optional<Solve> existing = solveRepository.findByUserAndStatus(user, SolveStatus.IN_PROGRESS);
	    
	    if (existing.isPresent()) {
	        throw new CustomException("이미 풀이가 진행 중입니다.");
	    }

	    // 2. 새 solve 생성
	    Solve newSolve = Solve.builder()
	            .user(user)
	            .status(SolveStatus.IN_PROGRESS)
	            .createdAt(LocalDateTime.now())
	            .build();

	    solveRepository.save(newSolve);

	    return new SolveStartResponseDTO(newSolve.getId(), newSolve.getStatus().name());
	}

}
