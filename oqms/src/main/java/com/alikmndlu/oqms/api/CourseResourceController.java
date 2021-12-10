package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.*;
import com.alikmndlu.oqms.model.Course;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.service.CourseService;
import com.alikmndlu.oqms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourseResourceController {

    private final UserService userService;

    private final CourseService courseService;

    @GetMapping("/course")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CourseIdTitleStartEndTeacherDto> getCourse(
            @RequestParam("id") Long courseId) {
        Course course = courseService.findById(courseId).get();

        // Transfer to customize Dto
        return ResponseEntity.ok().body(
                new CourseIdTitleStartEndTeacherDto(
                        course.getId(),
                        course.getTitle(),
                        course.getStart().toString(),
                        course.getEnd().toString(),
                        new UserIdNameUsernameDto(
                                course.getTeacher().getId(),
                                course.getTeacher().getName(),
                                course.getTeacher().getUsername()
                        )
                )
        );
    }

    @GetMapping("/courses")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CourseIdTitleStartEndTeacherDto>> getCourses() {
        log.info("CourseResourceController -> Get Courses");

        // Get courses list
        List<Course> courses = courseService.findAll();

        // Transfer to customize Dto
        return ResponseEntity.ok().body(
                CourseIdTitleStartEndTeacherDto.CourseListToCourseIdTitleStartEndTeacherDtoList(
                        courses
                )
        );
    }

    @PostMapping("/course/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCourse(@RequestBody CourseTitleStartEndTeacherUsernameDto courseDto) {
        log.info("CourseResourceController -> Add Course");
        courseService.addCourse(courseDto);
    }

    //TODO mapstruct library
    @PutMapping("/course/update/{courseId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCourse(@RequestBody CourseTitleStartEndTeacherUsernameDto courseDto, @PathVariable("courseId") Long courseId) {
        courseService.update(courseId, courseDto);
        log.info("CourseResourceController -> Update Course {}", courseId);
    }

    @DeleteMapping("/course/delete/{courseId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteById(courseId);
        log.info("CourseResourceController -> Delete Course {}", courseId);
    }

    @PostMapping("/course/add-student")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addStudentToCourse(@RequestBody UserUsernameCourseIdDto userCourseDto) {

        // check user already enroll in course
        if (courseService.isStudentEnrollInCourse(userCourseDto.getStudentUsername(), userCourseDto.getCourseId())) {
            return ResponseEntity.badRequest().build();
        }


        courseService.addStudentToCourse(
                userCourseDto.getCourseId(),
                userCourseDto.getStudentUsername());
        log.info("CourseResourceController -> Add User {} To Course {}",
                userCourseDto.getStudentUsername(),
                userCourseDto.getCourseId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/course/students/{courseId}")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public ResponseEntity<List<UserIdNameUsernameDto>> getCourseStudents(@PathVariable Long courseId) {
        log.info("CourseResourceController -> Get Enroll Students In Course {}", courseId);
        Course course = courseService.findById(courseId).get();
        return ResponseEntity.ok().body(
                UserIdNameUsernameDto.UserListToUserIdNameUsernameDtoList(
                        course.getStudents()
                )
        );
    }

    @DeleteMapping("/course/{course-id}/student/delete/{student-id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteStudentFromCourse(
            @PathVariable("course-id") Long courseId,
            @PathVariable("student-id") Long studentId) {

        Course course = courseService.findById(courseId).get();
        User student = userService.findById(studentId).get();

        List<User> students = course.getStudents().stream()
                .filter(e -> !e.getUsername().equals(student.getUsername()))
                .collect(Collectors.toList());

        course.setStudents(students);
        courseService.save(course);
        log.info("Student {} Has Been Removed From {} Course.", student.getUsername(), course.getTitle());
    }

    @GetMapping("/teacher/courses")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<List<CourseIdTitleStartEndDto>> getTeacherCourses() {
        log.info("CourseResourceController -> Teacher Fetch Courses List");
        String loggedInTeacherUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Course> courses = courseService.findTeacherCourses(loggedInTeacherUsername);
        return ResponseEntity.ok().body(
                CourseIdTitleStartEndDto
                        .CourseListToCourseIdTitleStartEndDtoList(courses)
        );
    }
}
