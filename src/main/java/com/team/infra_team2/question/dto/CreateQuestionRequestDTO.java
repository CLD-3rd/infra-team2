package com.team.infra_team2.question.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class CreateQuestionRequestDTO {

	// 문제 내용 (예: "JVM에 대한 설명으로 옳은 것은?")
	private String question_text;
	// 1~4 중 정답 하나
	private int correct_answer; 
	// 보기(선지) 리스트: 총 4개 (1번 ~ 4번 보기
	private List<ChoiceDTO> choices;
	
	@Getter 
	@Setter
    public static class ChoiceDTO {
        private int choice_number;  // 1, 2, 3, 4
        private String choice_text; // 보기 텍스트
    }
}
