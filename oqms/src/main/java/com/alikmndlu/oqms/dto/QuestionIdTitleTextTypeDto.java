package com.alikmndlu.oqms.dto;

import com.alikmndlu.oqms.model.Question;
import com.alikmndlu.oqms.model.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionIdTitleTextTypeDto {

    private Long id;

    private String title;

    private String text;

    private QuestionType type;

    public static List<QuestionIdTitleTextTypeDto> ListQuestionToQuestionIdTitleTextDtoList(List<Question> questions) {
        return questions.stream()
                .map(question -> new QuestionIdTitleTextTypeDto(
                        question.getId(),
                        question.getTitle(),
                        question.getText(),
                        question.getType()
                )).collect(Collectors.toList());
    }
}
