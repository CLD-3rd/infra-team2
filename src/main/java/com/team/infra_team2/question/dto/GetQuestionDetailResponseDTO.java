package com.team.infra_team2.question.dto;
import com.team.infra_team2.choice.entity.Choice;
import com.team.infra_team2.question.entity.Question;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 문제 단건 조회 응답 DTO
 * API 명세서의 문제 단건 조회 응답 형식을 구현함
 */
public class GetQuestionDetailResponseDTO {
    // TODO: API 명세서에는 'id'로 명시됨. 추후 필드명 변경 검토 필요
    private Long question_id; //API 명세의 id필드임
    private String question_text;
    // TODO: API 명세서에는 'choice'로 명시됨. 추후 필드명 변경 검토 필요
    private List<GetChoiceResponseDTO> choice_list;
    
    /**
     * 문제 상세 DTO 생성자
     * 
     * @param questionId 문제 ID
     * @param questionText 문제 내용
     * @param choiceList 선택지 목록
     */
    private GetQuestionDetailResponseDTO(Long questionId, String questionText, List<GetChoiceResponseDTO> choiceList) {
        this.question_id = questionId;
        this.question_text = questionText;
        this.choice_list = choiceList;
    }
    
    /**
     * Question 엔티티와 Choice 목록으로부터 DTO를 생성하는 팩토리 메서드
     * 
     * @param question 문제 엔티티
     * @param choices 선택지 엔티티 목록
     * @return 변환된 DTO 객체
     */
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
    
    /**
     * 원시 값을 통해 DTO를 생성하는 팩토리 메서드
     * 
     * @param questionId 문제 ID
     * @param questionText 문제 내용
     * @param choiceList 선택지 DTO 목록
     * @return 생성된 DTO 객체
     */
    public static GetQuestionDetailResponseDTO of(Long questionId, String questionText, List<GetChoiceResponseDTO> choiceList) {
        return new GetQuestionDetailResponseDTO(questionId, questionText, choiceList);
    }
    
    // Getter 메서드
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