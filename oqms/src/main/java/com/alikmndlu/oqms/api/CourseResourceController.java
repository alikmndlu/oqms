package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.model.Course;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.dto.*;
import com.alikmndlu.oqms.service.CourseService;
import com.alikmndlu.oqms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
public class CourseResourceController {

//    private final UserService userService;
//
//    private final CourseService courseService;
//
//    @GetMapping("/admin/courses")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<ShowCourseDto>> getCourses() {
//        List<Course> courses = courseService.getCourses();
//        List<ShowCourseDto> result = new ArrayList<>();
//
//        courses.forEach(course -> {
//            result.add(new ShowCourseDto(
//                    course.getId(),
//                    course.getTitle(),
//                    course.getStart().toString(),
//                    course.getEnd().toString(),
//                    new ShowTeacherDto(
//                            course.getTeacher().getId(),
//                            course.getTeacher().getName(),
//                            course.getTeacher().getUsername())
//            ));
//        });
//
//        return ResponseEntity.ok().body(result);
//    }
//
//    @PostMapping("/admin/course/add")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public void addCourse(@RequestBody AddCourseDto courseDto) {
//        Course course = new Course(
//                courseDto.getTitle(),
//                LocalDate.parse(courseDto.getStart()),
//                LocalDate.parse(courseDto.getEnd()),
//                userService.findByUsername(courseDto.getTeacher()).get()
//        );
//        courseService.saveCourse(course);
//        log.info("Add New Course -> {}", courseDto.getTitle());
//    }
//
//    //TODO mapstruct library
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
