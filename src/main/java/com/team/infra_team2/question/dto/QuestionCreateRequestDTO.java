package com.team.infra_team2.question.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionCreateRequestDTO {

	// 문제 내용 (예: "JVM에 대한 설명으로 옳은 것은?")
	private String questionText;
	// 1~4 중 정답 하나
	private int correctAnswer; 
	// 보기(선지) 리스트: 총 4개 (1번 ~ 4번 보기
	private List<ChoiceCreateRequestDTO> choicesCreate;
	
	@Getter 
	@Setter
	@NoArgsConstructor
    public static class ChoiceCreateRequestDTO {
        private int choiceNumber;  // 1, 2, 3, 4
        private String choiceText; // 보기 텍스트
    }
}
