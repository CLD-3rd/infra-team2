package com.team.infra_team2.question.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

import com.team.infra_team2.question.entity.Question;

/**
 * 문제 전체 조회 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetQuestionListResponseDTO{
    private Long question_id; // <- API 명세서 - 문제전체 조회 - 응답의 id 필드
    private String question_text;
    private String author;
    private String created_at;
    
    // Question 엔티티에서 DTO로 변환하는 정적 팩토리 메서드
 // Question 엔티티에서 DTO로 변환하는 정적 팩토리 메서드
    public static GetQuestionListResponseDTO from(Question question) {
        return new GetQuestionListResponseDTO(
            question.getId(),
            question.getQuestion_text(),
            // 수정된 코드
            question.getUser() != null ? question.getUser().getId().toString() : "관리자",
            question.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }
    
    // 원시 데이터로부터 DTO 생성하는 정적 팩토리 메서드
    public static GetQuestionListResponseDTO of(Long id, String questionText, String author, String createdAt) {
        return new GetQuestionListResponseDTO(id, questionText, author, createdAt);
    }
}