package com.team.infra_team2.choice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.infra_team2.choice.entity.Choice;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    // 문제 ID로 해당 문제의 선택지들을 조회하는 메서드
    List<Choice> findByQuestionId(Long questionId);
}