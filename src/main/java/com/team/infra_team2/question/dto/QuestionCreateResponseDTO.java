package com.team.infra_team2.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 문제 등록 완료 시 프론트에 응답으로 보내는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreateResponseDTO {
	// 등록된 문제의 ID
    private Long questionId;

    // 응답 메시지 (예: "문제가 등록되었습니다.")
    private String message;
}
