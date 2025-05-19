package com.team.infra_team2.question.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.team.infra_team2.choice.entity.Choice;
import com.team.infra_team2.choice.repository.ChoiceRepository;
import com.team.infra_team2.question.dto.QuestionCreateRequestDTO;
import com.team.infra_team2.question.dto.QuestionCreateResponseDTO;
import com.team.infra_team2.question.entity.Question;
import com.team.infra_team2.question.repository.QuestionRepository;
import com.team.infra_team2.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	private final QuestionRepository questionRepository;
	private final ChoiceRepository choiceRepository;
	 
	public QuestionCreateResponseDTO createQuestion(QuestionCreateRequestDTO requestDTO, User user) {
		//Question 엔티티 생성 및 값 주입
	    Question question = new Question();
	    question.setQuestion_text(requestDTO.getQuestionText());
	    question.setCorrect_answer(requestDTO.getCorrectAnswer());
	    question.setUser(user);
	    
	    Question savedQuestion = questionRepository.save(question);
	    
	    List<Choice> choices = new ArrayList<>();
	    for(int i = 0; i < requestDTO.getChoicesCreate().size(); i++) {
	    	QuestionCreateRequestDTO.ChoiceCreateRequestDTO choiceDTO = requestDTO.getChoicesCreate().get(i);
	    	String choice_text = choiceDTO.getChoiceText();
	    	int choice_number = choiceDTO.getChoiceNumber();
	    	
	    	Choice choice = new Choice();
	    	choice.setChoice_text(choice_text);
	    	choice.setChoice_number(choice_number);
	    	choice.setQuestion(savedQuestion);
	    	choice.setIs_correct(choice_number == requestDTO.getCorrectAnswer());
	    	
	    	choices.add(choice);
	    	
	    }
	    choiceRepository.saveAll(choices);
	    return new QuestionCreateResponseDTO(savedQuestion.getId(), "문제가 등록되었습니다.");
	}
}
