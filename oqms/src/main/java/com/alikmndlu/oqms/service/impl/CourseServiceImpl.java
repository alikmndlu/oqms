package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.dto.CourseTitleStartEndTeacherUsernameDto;
import com.alikmndlu.oqms.model.Course;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.repository.CourseRepository;
import com.alikmndlu.oqms.service.CourseService;
import com.alikmndlu.oqms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

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
    public void addCourse(CourseTitleStartEndTeacherUsernameDto courseDto) {
        courseRepository.save(
                new Course(
                        courseDto.getTitle(),
                        LocalDate.parse(courseDto.getStart()),
                        LocalDate.parse(courseDto.getEnd()),
                        userService.findByUsername(courseDto.getTeacherUsername()).get()
                )
        );
    }

    @Override
    public void update(Long courseId, CourseTitleStartEndTeacherUsernameDto courseDto) {
        Course course = courseRepository.findById(courseId).get();
        course.setTitle(courseDto.getTitle());
        course.setStart(LocalDate.parse(courseDto.getStart()));
        course.setEnd(LocalDate.parse(courseDto.getEnd()));
        course.setTeacher(userService.findByUsername(courseDto.getTeacherUsername()).get());

        courseRepository.save(course);
    }

    @Override
    public void addStudentToCourse(Long courseId, String studentUsername) {
        User student = userService.findByUsername(studentUsername).get();
        Course course = courseRepository.findById(courseId).get();
        course.getStudents().add(student);
        save(course);
    }

    @Override
    public List<Course> findTeacherCourses(String loggedInTeacherUsername) {
        User teacher = userService.findByUsername(loggedInTeacherUsername).get();
        return courseRepository.findByTeacherId(teacher.getId());
    }
}
