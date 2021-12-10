package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.*;
import com.alikmndlu.oqms.model.*;
import com.alikmndlu.oqms.model.enums.QuestionType;
import com.alikmndlu.oqms.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

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

    private final ExamService examService;

    @PostMapping("/teacher/quiz/{quiz-id}/question/{question-id}/{score}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void addQuestionToQuiz(
            @PathVariable("question-id") Long questionId,
            @PathVariable("quiz-id") Long quizId,
            @PathVariable Long score) {

        quizQuestionService.save(
                new QuizQuestion(
                        quizService.findById(quizId).get(),
                        questionService.findById(questionId).get(),
                        score
                )
        );
    }

//    @GetMapping("/quiz/{quiz-id}/questions")
//    public ResponseEntity<List<QuizQuestionQuestionIdScoreDto>> getQuestionsOfQuiz(
//            @PathVariable("quiz-id") Long quizId) {
//
//        Quiz quiz = quizService.findById(quizId).get();
//        List<QuizQuestion> quizQuestions = quizQuestionService.findByQuiz(quiz);
//
//        return ResponseEntity.ok().body(
//                QuizQuestionQuestionIdScoreDto.QuizQuestionListToQuizQuestionQuestionIdScoreDtoList(
//                        quizQuestions
//                )
//        );
//    }

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
    public void deleteQuiz(@PathVariable("quizz-id") Long quizId) {
        quizService.deleteById(quizId);
    }

    // update quiz
    @PutMapping("teacher/quiz/update")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteQuiz(@RequestBody QuizIdTitleInfoTimeDto quizzDto) {
        quizService.updateQuiz(quizzDto);
    }

    @GetMapping("/teacher/quizz/{quizz-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<QuizIdTitleInfoTimeDto> getQuiz(@PathVariable("quizz-id") Long quizId) {
        Quiz quiz = quizService.findById(quizId).get();
        return ResponseEntity.ok().body(
                new QuizIdTitleInfoTimeDto(
                        quiz.getId(),
                        quiz.getTitle(),
                        quiz.getInfo(),
                        quiz.getTime(),
                        quiz.getIsComplete()
                )
        );
    }

    @GetMapping("/student/quiz/{quiz-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> getQuestionsOfQuizForStudent(
            @PathVariable("quiz-id") Long quizId) {

        Quiz quiz = quizService.findById(quizId).get();
        System.out.println(quiz);
        List<QuizQuestion> quizQuestions = quizQuestionService.findByQuiz(quiz);
        System.out.println(quizQuestions.size());

        List<Map<String, Object>> result = new ArrayList<>();

        for (QuizQuestion quizQuestion : quizQuestions) {
            Question question = quizQuestion.getQuestion();
            Map<String, Object> map = new HashMap<>();
            map.put("quizQuestionId", quizQuestion.getId());
            map.put("score", quizQuestion.getScore());
            map.put("text", question.getText());
            map.put("title", question.getTitle());
            map.put("type", question.getType().name());

            if (question.getType().equals(QuestionType.MULTI_SELECT)) {
                map.put("answers", question.getAnswers().stream()
                        .map(Answer::getText)
                        .collect(Collectors.toList()));
            }
            result.add(map);
        }

        return ResponseEntity.ok().body(result);
    }

    // add question to quiz with score
    @PostMapping("/teacher/quiz/question/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void addQuestionToQuizWithScore(@RequestBody QuizIdQuestionIdScore dto) {
        quizQuestionService.addQuestionToQuiz(
                dto.getQuizId(),
                dto.getQuestionId(),
                dto.getScore()
        );
    }

    // delete question from quiz
    @DeleteMapping("/teacher/quiz/question/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteQuestionFromQuiz(@RequestBody QuizIdQuestionId dto) {
        quizQuestionService.deleteQuestionFromQuiz(
                dto.getQuizId(),
                dto.getQuestionId()
        );
    }

    // get student enroll courses
    @GetMapping("/student/courses")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<List<CourseIdTitleStartEndTeacherDto>> getStudentEnrollCourses() {
        User student = userService.getLoggedInUser();
        List<Course> courses = courseService.getStudentEnrollCourses(student);
        return ResponseEntity.ok().body(
                CourseIdTitleStartEndTeacherDto.CourseListToCourseIdTitleStartEndTeacherDtoList(courses)
        );
    }

    // get quiz detail for student
    @GetMapping("/student/quiz/detail/{quiz-id}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<QuizIdTitleInformationTimeDto> getQuizDetailForStudent(@PathVariable("quiz-id") Long quizId) {
        Quiz quiz = quizService.findById(quizId).get();
        return ResponseEntity.ok().body(
                QuizIdTitleInformationTimeDto.convertQuizToQuizIdTitleInformationTimeDto(quiz)
        );
    }

    // get completed quizzes detail of course
    @GetMapping("/student/course/{course-id}/quizzes")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<List<QuizIdTitleInformationTimeDto>> getQuizzesOfCourse(
            @PathVariable("course-id") Long courseId) {

        // get all quizzes of course
        List<Quiz> quizzes = quizService.findByCourseId(courseId);

        // filter base on that quiz is complete or not
        quizzes = quizzes.stream()
                .filter(Quiz::getIsComplete)
                .collect(Collectors.toList());

        // return completed quizzes
        return ResponseEntity.ok().body(
                quizzes.stream()
                        .map(QuizIdTitleInformationTimeDto::convertQuizToQuizIdTitleInformationTimeDto)
                        .collect(Collectors.toList())

        );
    }

    // get quiz time
    @GetMapping("/student/quiz/{quiz-id}/time")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<Long> getQuizTime(@PathVariable("quiz-id") Long quizId){
        return ResponseEntity.ok().body(
                quizService.findById(quizId).get().getTime()
        );
    }
}
