package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.model.Course;
import com.alikmndlu.oqms.dto.*;
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
    public void addCourse(@RequestBody CourseInsertDto courseDto) {
        log.info("CourseResourceController -> Add Course");
        courseService.addCourse(courseDto);
    }

      //TODO mapstruct library
//    @PutMapping("/admin/course/update")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public void updateCourse(@RequestBody UpdateCourseDto courseDto) {
//        Course course = courseService.findById(courseDto.getId()).get();
//        course.setTitle(courseDto.getTitle());
//        course.setStart(LocalDate.parse(courseDto.getStart()));
//        course.setEnd(LocalDate.parse(courseDto.getEnd()));
//        course.setTeacher(userService.findByUsername(courseDto.getTeacher()).get());
//        courseService.saveCourse(course);
//        log.info("Update Course -> id:{}", course.getId());
//    }
//
//    @PostMapping("/admin/add-student-to-course")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public void addStudentToCourse(@RequestBody AddUserToCourseDto addUserToCourseDto) {
//        User student = userService.findByUsername(addUserToCourseDto.getStudentUsername()).get();
//        Course course = courseService.findById(addUserToCourseDto.getCourseId()).get();
//
//        course.getStudents().add(student);
//        log.info("User {} Add To Course {}", student.getUsername(), course.getTitle());
//    }
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
