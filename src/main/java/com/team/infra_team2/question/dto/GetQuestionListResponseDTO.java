package com.team.infra_team2.question.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.format.DateTimeFormatter;
import com.team.infra_team2.question.entity.Question;

/**
 * 문제 전체 조회 응답 DTO
 * API 명세서의 문제 목록 조회 응답 형식을 구현함
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetQuestionListResponseDTO {
    private Long questionId; // snake_case에서 camelCase로 변경
    private String questionText; // snake_case에서 camelCase로 변경
    private String author;
    private String createdAt; // snake_case에서 camelCase로 변경

    /**
     * Question 엔티티로부터 DTO를 생성하는 팩토리 메서드
     *
     * @param question 문제 엔티티
     * @return 변환된 DTO 객체
     */
    public static GetQuestionListResponseDTO from(Question question) {
        return new GetQuestionListResponseDTO(
            question.getId(),
            question.getQuestionText(),
            // User가 없으면 '관리자'로 표시
            question.getUser() != null ? question.getUser().getId().toString() : "관리자",
            question.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }

    /**
     * 원시 값을 통해 DTO를 생성하는 팩토리 메서드
     *
     * @param id 문제 ID
     * @param questionText 문제 내용
     * @param author 작성자
     * @param createdAt 작성일
     * @return 생성된 DTO 객체
     */
    public static GetQuestionListResponseDTO of(Long id, String questionText, String author, String createdAt) {
        return new GetQuestionListResponseDTO(id, questionText, author, createdAt);
    }
}