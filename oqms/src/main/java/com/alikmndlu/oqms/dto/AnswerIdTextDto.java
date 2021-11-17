package com.alikmndlu.oqms.dto;

import com.alikmndlu.oqms.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerIdTextDto {

    private Long id;

    private String text;

    public static List<AnswerIdTextDto> AnswerListToAnswerIdTextDtoList(List<Answer> answers) {
        return answers.stream()
                .map(answer -> new AnswerIdTextDto(
                        answer.getId(),
                        answer.getText()
                )).collect(Collectors.toList());
    }
}
