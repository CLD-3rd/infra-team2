package com.team.infra_team2.question.dto;

import com.team.infra_team2.choice.entity.Choice;
import com.team.infra_team2.question.entity.Question;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 문제 단건 조회 응답 DTO
 */
public class GetQuestionDetailResponseDTO {
    private Long question_id; //API 명세의 id필드임
    private String question_text;
    private List<GetChoiceResponseDTO> choice_list;

    // 생성자
    private GetQuestionDetailResponseDTO(Long questionId, String questionText, List<GetChoiceResponseDTO> choiceList) {
        this.question_id = questionId;
        this.question_text = questionText;
        this.choice_list = choiceList;
    }

    // Entity → DTO 변환 정적 팩토리 메서드
    public static GetQuestionDetailResponseDTO from(Question question, List<Choice> choices) {
        List<GetChoiceResponseDTO> choiceDTOList = choices.stream()
            .map(GetChoiceResponseDTO::from)
            .collect(Collectors.toList());

        return new GetQuestionDetailResponseDTO(
            question.getId(),
            question.getQuestion_text(),
            choiceDTOList
        );
    }

    // 원시 값으로부터 생성하는 정적 팩토리 메서드
    public static GetQuestionDetailResponseDTO of(Long questionId, String questionText, List<GetChoiceResponseDTO> choiceList) {
        return new GetQuestionDetailResponseDTO(questionId, questionText, choiceList);
    }

    // Getter
    public Long getQuestion_id() {
        return question_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public List<GetChoiceResponseDTO> getChoice_list() {
        return choice_list;
    }
}
