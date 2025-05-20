package com.team.infra_team2.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.infra_team2.question.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
