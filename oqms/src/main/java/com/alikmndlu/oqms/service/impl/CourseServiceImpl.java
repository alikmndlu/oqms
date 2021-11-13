package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.model.Course;
import com.alikmndlu.oqms.repository.CourseRepository;
import com.alikmndlu.oqms.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public Course saveCourse(Course course) {
        log.info("Save Course -> {}", course.getTitle());
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }
}
