package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.QuizIdTitleInfoTimeDto;
import com.alikmndlu.oqms.dto.QuizTitleInfoTimeCourseIdDto;
import com.alikmndlu.oqms.service.CourseService;
import com.alikmndlu.oqms.service.QuizService;
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

    @GetMapping("teacher/quizes/course/{courseId}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<List<QuizIdTitleInfoTimeDto>> getQuizzes(@PathVariable("courseId") Long courseId) {
        List<QuizIdTitleInfoTimeDto> quizzes =
                QuizIdTitleInfoTimeDto.quizListToQuizIdTitleInfoTimeDtoList(
                        quizService.findByCourseId(courseId)
                );

        return ResponseEntity.ok().body(quizzes);
    }

    @DeleteMapping("teacher/quiz/delete/{quizz-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteQuiz(@PathVariable("quizz-id") Long quizId){
        quizService.deleteById(quizId);
    }
}
