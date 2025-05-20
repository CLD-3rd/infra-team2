package com.team.infra_team2.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.infra_team2.question.entity.Question;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // 문제 ID로 문제 조회
    Optional<Question> findById(Long id);

    // 페이지네이션을 적용한 문제 목록 조회 (ID 내림차순)
    Page<Question> findAllByOrderByIdDesc(Pageable pageable);

    // 특정 사용자가 생성한 문제 목록 조회 (ID 내림차순)
    Page<Question> findByUserIdOrderByIdDesc(Long userId, Pageable pageable);
}
