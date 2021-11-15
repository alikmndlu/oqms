package com.alikmndlu.oqms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuizTitleInfoTimeCourseIdDto {

    private String title;

    private String info;

    private Long time;

    private Long courseId;
}
