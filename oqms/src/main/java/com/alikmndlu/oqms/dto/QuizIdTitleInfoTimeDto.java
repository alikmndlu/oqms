package com.alikmndlu.oqms.dto;

import com.alikmndlu.oqms.model.Quiz;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuizIdTitleInfoTimeDto {

    private Long id;

    private String title;

    private String info;

    private Long time;

    public static List<QuizIdTitleInfoTimeDto> quizListToQuizIdTitleInfoTimeDtoList(List<Quiz> quizzes){
        return quizzes.stream()
                .map(quiz -> new QuizIdTitleInfoTimeDto(
                        quiz.getId(),
                        quiz.getTitle(),
                        quiz.getInfo(),
                        quiz.getTime()
                )).collect(Collectors.toList());
    }
}
