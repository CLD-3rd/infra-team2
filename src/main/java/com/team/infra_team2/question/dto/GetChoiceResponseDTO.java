package com.team.infra_team2.question.dto;

import com.team.infra_team2.choice.entity.Choice;

/**
 * 선택지 응답 DTO
 */
public class GetChoiceResponseDTO {
    private Integer choice_number;
    private String choice_text;

    // 생성자
    private GetChoiceResponseDTO(Integer choice_number, String choice_text) {
        this.choice_number = choice_number;
        this.choice_text = choice_text;
    }

    // Entity → DTO 변환 정적 팩토리 메서드
    public static GetChoiceResponseDTO from(Choice choice) {
        return new GetChoiceResponseDTO(
            choice.getChoice_number(),
            choice.getChoice_text()
        );
    }

    // 원시 값으로부터 생성하는 정적 팩토리 메서드
    public static GetChoiceResponseDTO of(Integer choiceNumber, String choiceText) {
        return new GetChoiceResponseDTO(choiceNumber, choiceText);
    }

    // Getter
    public Integer getChoice_number() {
        return choice_number;
    }

    public String getChoice_text() {
        return choice_text;
    }
}
