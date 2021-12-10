package com.alikmndlu.oqms.dto;

import com.alikmndlu.oqms.model.Question;
import com.alikmndlu.oqms.model.enums.QuestionType;
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
public class QuestionTitleTextDto {

    private String title;

    private String text;
}
