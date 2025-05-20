package com.team.infra_team2.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.infra_team2.answer.entity.Answer;
import com.team.infra_team2.question.entity.Question;
import com.team.infra_team2.solve.entity.Solve;

public interface AnswerRepository extends JpaRepository<Answer, Long>{
	// 특정 solve에 대한 총 답안 수
    int countBySolve(Solve solve);

    // 특정 solve에 대한 정답 개수
    int countBySolveAndIsCorrectTrue(Solve solve);
    

    void deleteAllByQuestion(Question question);  // 이거 추가
}
