package com.team.infra_team2.choice.entity;

import com.team.infra_team2.common.entity.BaseEntity;
import com.team.infra_team2.question.entity.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Entity
public class Choice extends BaseEntity {
    
    @Id
    @Column(name = "choice_id")
    private Long id;
    
    @Column(name = "choice_text")
    private String choiceText;
    
    @Column(name = "is_correct")
    private Boolean isCorrect;
    
    @Column(name = "choice_number")
    private Integer choiceNumber;
    
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
