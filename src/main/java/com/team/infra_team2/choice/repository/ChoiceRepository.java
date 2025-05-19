package com.team.infra_team2.choice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.infra_team2.choice.entity.Choice;

/**
 * 문제의 선택지 정보를 관리하는 리포지토리
 * JPA를 활용하여 Choice 엔티티에 대한 데이터 접근을 처리함
 */
@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
	/**
     * 특정 문제에 대한 선택지 목록을 선택지 번호 순으로 조회
     * 
     * @param questionId 문제 ID
     * @return 선택지 번호 순으로 정렬된 선택지 목록
     */
    List<Choice> findByQuestionIdOrderByChoice_number(Long questionId);
}