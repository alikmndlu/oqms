package com.alikmndlu.oqms.dto;

import com.alikmndlu.oqms.model.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamAnswerIdTypeScoreQuestionAnswer {

    private Long examAnswerId;

    private QuestionType type;

    private Long score;
    
    private String question;

    private String answer;
}
