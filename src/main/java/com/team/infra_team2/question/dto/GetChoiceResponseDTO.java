package com.team.infra_team2.question.dto;

import com.team.infra_team2.choice.entity.Choice;
import lombok.Getter;

/**
 * 선택지 정보를 응답하기 위한 DTO 클래스
 * API 명세서의 'choice' 필드 내부 객체를 표현함
 */
@Getter // Lombok @Getter 어노테이션 추가
public class GetChoiceResponseDTO {
    // API 명세서 필드: choice_number
    private Integer choice_number;
    // API 명세서 필드: choice_text
    private String choice_text;
    
    // camelCase 필드 추가 (템플릿 렌더링용)
    private Integer choiceNumber;
    private String choiceText;

    /**
     * 선택지 DTO 생성자
     *
     * @param choice_number 선택지 번호
     * @param choice_text 선택지 내용
     */
    private GetChoiceResponseDTO(Integer choice_number, String choice_text) {
        this.choice_number = choice_number;
        this.choice_text = choice_text;
        
        // camelCase 필드도 같이 설정
        this.choiceNumber = choice_number;
        this.choiceText = choice_text;
    }

    /**
     * Choice 엔티티를 DTO로 변환하는 팩토리 메서드
     *
     * @param choice Choice 엔티티
     * @return 변환된 DTO 객체
     */
    public static GetChoiceResponseDTO from(Choice choice) {
        return new GetChoiceResponseDTO(
            choice.getChoiceNumber(),
            choice.getChoiceText()
        );
    }

    /**
     * 원시 값을 통해 DTO를 생성하는 팩토리 메서드
     *
     * @param choiceNumber 선택지 번호
     * @param choiceText 선택지 내용
     * @return 생성된 DTO 객체
     */
    public static GetChoiceResponseDTO of(Integer choiceNumber, String choiceText) {
        return new GetChoiceResponseDTO(choiceNumber, choiceText);
    }

    // snake_case getter 메서드는 유지 (API 호환성)
    public Integer getChoice_number() {
        return choice_number;
    }

    public String getChoice_text() {
        return choice_text;
    }
}