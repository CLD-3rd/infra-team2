package com.team.infra_team2.choice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.infra_team2.choice.entity.Choice;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
