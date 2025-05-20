package com.team.infra_team2.question.dto;
import com.team.infra_team2.choice.entity.Choice;
import com.team.infra_team2.question.entity.Question;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter; // Lombok @Getter 추가

/**
 * 문제 단건 조회 응답 DTO
 * API 명세서의 문제 단건 조회 응답 형식을 구현함
 */
@Getter // Lombok @Getter 어노테이션 추가
public class GetQuestionDetailResponseDTO {
    // 필드명은 그대로 유지
    private Long questionId; //API 명세의 id필드임
    private String questionText;
    private List<GetChoiceResponseDTO> choiceList;

    /**
     * 문제 상세 DTO 생성자
     */
    private GetQuestionDetailResponseDTO(Long questionId, String questionText, List<GetChoiceResponseDTO> choiceList) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.choiceList = choiceList;
    }

    /**
     * Question 엔티티와 Choice 목록으로부터 DTO를 생성하는 팩토리 메서드
     */
    public static GetQuestionDetailResponseDTO from(Question question, List<Choice> choices) {
        List<GetChoiceResponseDTO> choiceDTOList = choices.stream()
            .map(GetChoiceResponseDTO::from)
            .collect(Collectors.toList());
        return new GetQuestionDetailResponseDTO(
            question.getId(),
            question.getQuestionText(),
            choiceDTOList
        );
    }

    /**
     * 원시 값을 통해 DTO를 생성하는 팩토리 메서드
     */
    public static GetQuestionDetailResponseDTO of(Long questionId, String questionText, List<GetChoiceResponseDTO> choiceList) {
        return new GetQuestionDetailResponseDTO(questionId, questionText, choiceList);
    }

    // JSON 직렬화를 위한 별도의 메서드 (API 호환성 유지)
    public Long getQuestion_id() {
        return questionId;
    }

    public String getQuestion_text() {
        return questionText;
    }

    public List<GetChoiceResponseDTO> getChoice_list() {
        return choiceList;
    }
}