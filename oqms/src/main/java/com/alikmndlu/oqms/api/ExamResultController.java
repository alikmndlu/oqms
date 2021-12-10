package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.model.ExamResult;
import com.alikmndlu.oqms.model.Quiz;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.service.ExamResultService;
import com.alikmndlu.oqms.service.QuizService;
import com.alikmndlu.oqms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExamResultController {

    private final ExamResultService examResultService;

    private final QuizService quizService;

    private final UserService userService;

    // is student enter to quiz before or not?
    @GetMapping("/student/quiz/{quiz-id}/check")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<Boolean> checkStudentEnterToQuizBeforeOrNot(@PathVariable("quiz-id") Long quizId) {
        User student = userService.getLoggedInUser();
        Quiz quiz = quizService.findById(quizId).get();
        return ResponseEntity.ok().body(
                examResultService.isStudentEnterToQuizBefore(student, quiz)
        );
    }

    // start exam
    @PostMapping("/student/start-exam/{quiz-id}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public void startExam(@PathVariable("quiz-id") Long quizId) {
        Quiz quiz = quizService.findById(quizId).get();
        User student = userService.getLoggedInUser();

        if (!examResultService.isExistsByQuizIdAndStudentId(student, quiz)) {
            examResultService.save(new ExamResult(
                    student,
                    quiz,
                    false,
                    LocalDateTime.now(),
                    null
            ));
        }
    }
}
