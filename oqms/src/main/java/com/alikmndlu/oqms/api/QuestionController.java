package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.*;
import com.alikmndlu.oqms.model.*;
import com.alikmndlu.oqms.model.enums.QuestionType;
import com.alikmndlu.oqms.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    private final AnswerService answerService;

    private final UserService userService;

    private final QuizService quizService;

    private final QuizQuestionService quizQuestionService;

    @GetMapping("/teacher/question/{question-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> getQuestion(@PathVariable("question-id") Long questionId) {
        Question question = questionService.findById(questionId).get();

        Map<String, Object> map = new HashMap<>();
        map.put("id", question.getId());
        map.put("type", question.getType().name());
        if (question.getType().equals(QuestionType.MULTI_SELECT)) {
            map.put("answers", question.getAnswers().stream().map(Answer::getText).collect(Collectors.toList()));
            map.put("trueAnswer", question.getTrueAnswer().getText());
        }
        map.put("title", question.getTitle());
        map.put("text", question.getText());

        return ResponseEntity.ok().body(map);
    }

    // Insert Descriptive Question By Teacher
    @PostMapping("/teacher/question/descriptive/create")
    @GetMapping("hasRole('ROLE_TEACHER')")
    public void insertDescriptiveQuestion(@RequestBody QuestionTitleTextDto questionDto) {
        questionService.insertDescriptiveQuestion(questionDto);
    }

    // Insert MultiSelect Question By Teacher
    @PostMapping("/teacher/question/multiselect/create")
    @GetMapping("hasRole('ROLE_TEACHER')")
    public void insertMultiSelectQuestion(@RequestBody QuestionTitleTextAnswersTrueAnswerDto questionDto) {
        questionService.insertMultiSelectQuestion(questionDto);
    }

    @PostMapping("/teacher/question/create")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void insertMultiSelectQuestion(@RequestBody MultiSelectQuestionTitleTextDto questionDto) {
        questionService.insertMultiSelectQuestion(questionDto);
    }

    @PutMapping("/teacher/msq/update/{question-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void updateTitleAndTextQuestion(@RequestBody MultiSelectQuestionTitleTextDto questionDto, @PathVariable("question-id") Long questionId) {
        questionService.updateQuestion(questionDto, questionId);
    }

    // delete question
    @DeleteMapping("/teacher/question/delete/{question-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteQuestion(@PathVariable("question-id") Long questionId) {
        quizQuestionService.deleteByQuestionId(questionId);
        questionService.deleteById(questionId);
    }

    @PutMapping("/teacher/question/{question-id}/true-answer/{answer-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void setTrueAnswerForQuestion(
            @PathVariable("question-id") Long questionId,
            @PathVariable("answer-id") Long answerId) {

        answerService.attachTrueAnswerToQuestion(questionId, answerId);
    }

    @GetMapping("/teacher/question-bank/{teacher-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> getTeacherQuestionsFromQuestionBank(@PathVariable("teacher-id") Long teacherId) {
        Optional<User> optionalTeacher = userService.findById(teacherId);

        if (optionalTeacher.isEmpty() ||
                !optionalTeacher.get().getRoles().get(0).getName()
                        .equals("ROLE_TEACHER")) {
            return ResponseEntity.notFound().build();
        }

        if (!optionalTeacher.get().getUsername().equals(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        )) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(
                QuestionIdTitleTextTypeDto.ListQuestionToQuestionIdTitleTextDtoList(
                        questionService.findByTeacherId(teacherId)
                )
        );

    }


    // get questions from bank of questions base on loggedIn teacher
    @GetMapping("/teacher/bank-of-question/questions")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<List<?>> getTeacherQuestionsFromBank() {
        List<Question> questions = questionService.getTeacherQuestionsFromBankOfQuestion();
        return ResponseEntity.ok().body(
                questions.stream()
                        .map(questionService::questionObjectToQuestionDto)
                        .collect(Collectors.toList())
        );
    }

    // update descriptive question
    @PutMapping("/teacher/question/descriptive/update")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void updateDescriptiveQuestion(@RequestBody QuestionIdTitleTextDto questionDto) {
        questionService.updateDescriptiveQuestion(questionDto);
    }

    // update multiselect question
    @PutMapping("/teacher/question/multiselect/update")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void updateMultiSelectQuestion(@RequestBody QuestionIdTitleTextAnswersTrueAnswerDto questionDto) {
        questionService.updateMultiSelectQuestion(questionDto);
    }

    // get all the questions from bank
    // plus status base on added to quiz or not
    @GetMapping("/teacher/questions-plus-status/{quiz-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> getQuestionsPlusStatus(@PathVariable("quiz-id") Long quizId) {
        List<Question> questions = questionService.findByTeacherId(userService.getLoggedInUser().getId());

        List<QuestionIdTitleStatusDto> result = new ArrayList<>();
        questions.forEach(
                question -> result.add(new QuestionIdTitleStatusDto(
                        question.getId(),
                        question.getTitle(),
                        quizQuestionService.isQuestionAddedToQuiz(quizId, question.getId())
                )));

        return ResponseEntity.ok().body(result);
    }
}
