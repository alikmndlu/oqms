package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.model.Course;
import com.alikmndlu.oqms.dto.*;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.service.CourseService;
import com.alikmndlu.oqms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourseResourceController {

    private final UserService userService;

    private final CourseService courseService;

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
    public void addStudentToCourse(@RequestBody UserUsernameCourseIdDto userCourseDto) {
        courseService.addStudentToCourse(
                userCourseDto.getCourseId(),
                userCourseDto.getStudentUsername());
        log.info("CourseResourceController -> Add User {} To Course {}",
                userCourseDto.getStudentUsername(),
                userCourseDto.getCourseId());
    }
//
//    @GetMapping("admin/course/students/{courseId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<Collection<UserUsernameNameDto>> getCourseStudents(@PathVariable Long courseId) {
//        Course course = courseService.findById(courseId).get();
//        List<UserUsernameNameDto> students = new ArrayList<>();
//        course.getStudents()
//                .forEach(student -> {
//                    students.add(new UserUsernameNameDto(
//                       student.getName(),
//                       student.getUsername()
//                    ));
//                });
//        return ResponseEntity.ok().body(students);
//    }
}
