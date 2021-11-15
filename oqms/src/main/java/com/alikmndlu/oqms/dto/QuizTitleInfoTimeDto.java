package com.alikmndlu.oqms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuizTitleInfoTimeDto {

    private String title;

    private String info;

    private Long time;
}
