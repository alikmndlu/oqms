package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course saveCourse(Course course);
    Optional<Course> findById(Long id);
    List<Course> getCourses();
}
