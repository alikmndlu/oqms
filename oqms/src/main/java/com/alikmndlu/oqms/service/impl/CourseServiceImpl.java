package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.model.Course;
import com.alikmndlu.oqms.repository.CourseRepository;
import com.alikmndlu.oqms.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class CourseServiceImpl extends BaseServiceImpl<Course, Long, CourseRepository>
        implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository repository) {
        super(repository);
        this.courseRepository = repository;
    }
}
