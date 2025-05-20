package com.team.infra_team2.solve.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team.infra_team2.solve.constant.SolveStatus;
import com.team.infra_team2.solve.dto.SolveStartResponseDTO;
import com.team.infra_team2.solve.entity.Solve;
import com.team.infra_team2.solve.repository.SolveRepository;
import com.team.infra_team2.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolveService {

    private final SolveRepository solveRepository;

    @Transactional
    public SolveStartResponseDTO startSolve(User user) {

        // 진행 중인 풀이가 이미 있는지 확인
        List<Solve> inProgressList = solveRepository.findByUserAndStatus(user, SolveStatus.IN_PROGRESS);

        if (!inProgressList.isEmpty()) {
            throw new IllegalStateException("이미 진행 중인 풀이가 존재합니다.");
        }

        // 새 solve 생성
        Solve newSolve = Solve.builder()
                .user(user)
                .status(SolveStatus.IN_PROGRESS)
                .createdAt(LocalDateTime.now())
                .build();

        solveRepository.save(newSolve);

        return new SolveStartResponseDTO(newSolve.getId(), newSolve.getStatus().name());
    }
}
