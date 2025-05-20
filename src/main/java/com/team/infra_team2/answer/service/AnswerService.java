package com.team.infra_team2.answer.service;

import org.springframework.stereotype.Service;

import com.team.infra_team2.answer.dto.request.AnswerSubmitDetailRequestDTO;
import com.team.infra_team2.answer.dto.response.AnswerSubmitDetailResponseDTO;
import com.team.infra_team2.answer.entity.Answer;
import com.team.infra_team2.answer.repository.AnswerRepository;
import com.team.infra_team2.question.entity.Question;
import com.team.infra_team2.question.repository.QuestionRepository;
import com.team.infra_team2.solve.entity.Solve;
import com.team.infra_team2.solve.repository.SolveRepository;
import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.repository.UserRepository;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final SolveRepository solveRepository;
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public AnswerSubmitDetailResponseDTO submitAnswerDetail(
			AnswerSubmitDetailRequestDTO answerSubmitDetailRequest,
			Long solveId,
			PrincipalDetails principalDetails) {
		Solve solve = solveRepository.findById(solveId)
				.orElseThrow(() -> new IllegalArgumentException("Solve not found"));
		
		Question question = questionRepository.findById(answerSubmitDetailRequest.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
		
		User user = userRepository.findByUsername(principalDetails.getUsername());
		
		Boolean isCorrect = question.getCorrectAnswer().equals(answerSubmitDetailRequest.getSelectedAnswer());
		
		Answer answer = Answer.of(answerSubmitDetailRequest.getSelectedAnswer(),
				isCorrect, user,
				question, solve);
		
		answerRepository.save(answer);
		
		return AnswerSubmitDetailResponseDTO.of(answer.getId(), "Answer submitted successfully!");
	}
	
}
