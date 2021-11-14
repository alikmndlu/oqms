package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.dto.CourseInsertDto;
import com.alikmndlu.oqms.model.Course;
import com.alikmndlu.oqms.repository.CourseRepository;
import com.alikmndlu.oqms.service.CourseService;
import com.alikmndlu.oqms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
@Slf4j
public class CourseServiceImpl extends BaseServiceImpl<Course, Long, CourseRepository>
        implements CourseService {

    private final CourseRepository courseRepository;

    private final UserService userService;

    public CourseServiceImpl(CourseRepository repository, UserService userService) {
        super(repository);
        this.courseRepository = repository;
        this.userService = userService;
    }

    @Override
    public void addCourse(CourseInsertDto courseDto) {
        courseRepository.save(
                new Course(
                        courseDto.getTitle(),
                        LocalDate.parse(courseDto.getStart()),
                        LocalDate.parse(courseDto.getEnd()),
                        userService.findByUsername(courseDto.getTeacherUsername()).get()
                )
        );
    }
}
