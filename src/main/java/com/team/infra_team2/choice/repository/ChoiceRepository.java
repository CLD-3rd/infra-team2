package com.team.infra_team2.choice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.infra_team2.choice.entity.Choice;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findByQuestionIdOrderByChoiceNumber(Long questionId);
}