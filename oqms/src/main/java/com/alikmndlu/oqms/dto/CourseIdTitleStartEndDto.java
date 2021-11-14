package com.alikmndlu.oqms.dto;

import com.alikmndlu.oqms.model.Course;
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
public class CourseIdTitleStartEndDto {

    private Long id;

    private String title;

    private String start;

    private String end;

    public static List<CourseIdTitleStartEndDto> CourseListToCourseIdTitleStartEndDtoList(List<Course> courses) {
        return courses.stream()
                .map(course -> new CourseIdTitleStartEndDto(
                        course.getId(),
                        course.getTitle(),
                        course.getStart().toString(),
                        course.getEnd().toString()
                )).collect(Collectors.toList());
    }
}
