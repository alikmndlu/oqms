package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.QuizTitleInfoTimeCourseIdDto;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.service.CourseService;
import com.alikmndlu.oqms.service.QuizService;
import com.alikmndlu.oqms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuizResourceController {

    private final UserService userService;

    private final CourseService courseService;

    private final QuizService quizService;

    @PostMapping("/teacher/quiz/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void addQuiz(@RequestBody QuizTitleInfoTimeCourseIdDto quizDto) {
        quizService.addQuizForCourse(quizDto);
        log.info("New Quiz Added");
    }
}
