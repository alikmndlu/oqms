package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.dto.CourseTitleStartEndTeacherUsernameDto;
import com.alikmndlu.oqms.model.Course;
import com.alikmndlu.oqms.model.User;

import java.util.List;

public interface CourseService extends BaseService<Course, Long> {

    void addCourse(CourseTitleStartEndTeacherUsernameDto courseDto);

    void update(Long courseId, CourseTitleStartEndTeacherUsernameDto courseDto);

    void addStudentToCourse(Long courseId, String studentUsername);

    List<Course> findTeacherCourses(String loggedInTeacherUsername);

    boolean isStudentEnrollInCourse(String studentUsername, Long courseId);

    List<Course> getStudentEnrollCourses(User student);
}
