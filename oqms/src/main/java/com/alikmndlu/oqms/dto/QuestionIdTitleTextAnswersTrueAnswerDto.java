package com.alikmndlu.oqms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionIdTitleTextAnswersTrueAnswerDto {

    private Long id;

    private String title;

    private String text;

    private List<String> answers;

    private String trueAnswer;
}
