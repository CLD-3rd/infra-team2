package com.team.infra_team2.solve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.infra_team2.solve.constant.SolveStatus;
import com.team.infra_team2.solve.entity.Solve;
import com.team.infra_team2.user.entity.User;

public interface SolveRepository extends JpaRepository<Solve, Long> {
    List<Solve> findByUserAndStatus(User user, SolveStatus status);
}
