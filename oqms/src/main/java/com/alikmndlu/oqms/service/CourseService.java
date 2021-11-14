package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.dto.CourseInsertDto;
import com.alikmndlu.oqms.model.Course;
import com.alikmndlu.oqms.model.Role;

public interface CourseService extends BaseService<Course, Long> {

    void addCourse(CourseInsertDto courseDto);
}
