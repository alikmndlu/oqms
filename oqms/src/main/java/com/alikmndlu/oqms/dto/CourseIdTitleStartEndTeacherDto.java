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
public class CourseIdTitleStartEndTeacherDto {

    private Long id;

    private String title;

    private String start;

    private String end;

    private UserIdNameUsernameDto teacher;

    public static List<CourseIdTitleStartEndTeacherDto> CourseListToCourseIdTitleStartEndTeacherDtoList(List<Course> courses) {
        return courses.stream()
                .map(course -> new CourseIdTitleStartEndTeacherDto(
                        course.getId(),
                        course.getTitle(),
                        course.getStart().toString(),
                        course.getEnd().toString(),
                        new UserIdNameUsernameDto(
                                course.getTeacher().getId(),
                                course.getTeacher().getName(),
                                course.getTeacher().getUsername()
                        )
                )).collect(Collectors.toList());
    }
}
