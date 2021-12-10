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
public class QuizIdTitleInformationTimeDto {

    private Long id;

    private String title;

    private String info;

    private Long time;

    public static QuizIdTitleInformationTimeDto convertQuizToQuizIdTitleInformationTimeDto(Quiz quiz) {
        return new QuizIdTitleInformationTimeDto(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getInfo(),
                quiz.getTime()
        );
    }
}
