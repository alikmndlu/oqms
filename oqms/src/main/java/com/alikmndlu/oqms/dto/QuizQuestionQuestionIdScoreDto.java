package com.alikmndlu.oqms.dto;

import com.alikmndlu.oqms.model.QuizQuestion;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuizQuestionQuestionIdScoreDto {

    private Long questionId;

    private Long score;

    public static List<QuizQuestionQuestionIdScoreDto> QuizQuestionListToQuizQuestionQuestionIdScoreDtoList(List<QuizQuestion> quizQuestions){
        return quizQuestions.stream()
                .map(quizQuestion -> new QuizQuestionQuestionIdScoreDto(
                        quizQuestion.getQuestion().getId(),
                        quizQuestion.getScore()
                )).collect(Collectors.toList());
    }
}
