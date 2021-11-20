package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.QuizIdTitleInfoTimeDto;
import com.alikmndlu.oqms.dto.QuizQuestionQuestionIdScoreDto;
import com.alikmndlu.oqms.dto.QuizTitleInfoTimeCourseIdDto;
import com.alikmndlu.oqms.dto.QuizTitleInfoTimeDto;
import com.alikmndlu.oqms.model.QuizQuestion;
import com.alikmndlu.oqms.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuizResourceController {

    private final UserService userService;

    private final CourseService courseService;

    private final QuizService quizService;

    private final QuizQuestionService quizQuestionService;

    private final QuestionService questionService;

    @PostMapping("/teacher/quiz/{quiz-id}/question/{question-id}/{score}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void addQuestionToQuiz(
            @PathVariable("question-id") Long questionId,
            @PathVariable("quiz-id") Long quizId,
            @PathVariable Long score){

        quizQuestionService.save(
                new QuizQuestion(
                       quizService.findById(quizId).get(),
                       questionService.findById(questionId).get(),
                        score
                )
        );
    }

    @GetMapping("/quiz/{quiz-id}/questions")
    public ResponseEntity<List<QuizQuestionQuestionIdScoreDto>> getQuestionsOfQuiz(
            @PathVariable("quiz-id") Long quizId){

        List<QuizQuestion> quizQuestions = quizQuestionService.findByQuizId(quizId);

        return ResponseEntity.ok().body(
                QuizQuestionQuestionIdScoreDto.QuizQuestionListToQuizQuestionQuestionIdScoreDtoList(
                        quizQuestions
                )
        );
    }

    @PostMapping("/teacher/quiz/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void addQuiz(@RequestBody QuizTitleInfoTimeCourseIdDto quizDto) {
        quizService.addQuizForCourse(quizDto);
        log.info("New Quiz Added");
    }

    @GetMapping("teacher/quizzes/course/{courseId}")
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

    @PutMapping("teacher/quiz/update")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteQuiz(@RequestBody QuizIdTitleInfoTimeDto quizzDto){
        quizService.updateQuiz(quizzDto);
    }
}
