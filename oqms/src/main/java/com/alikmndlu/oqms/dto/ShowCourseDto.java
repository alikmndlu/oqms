package com.alikmndlu.oqms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowCourseDto {

    private Long id;

    private String title;

    private String start;

    private String end;

    private ShowTeacherDto teacher;
}
