package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.MultiSelectQuestionTitleTextDto;
import com.alikmndlu.oqms.dto.QuestionIdTitleTextTypeDto;
import com.alikmndlu.oqms.model.Answer;
import com.alikmndlu.oqms.model.Question;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.model.enums.QuestionType;
import com.alikmndlu.oqms.service.AnswerService;
import com.alikmndlu.oqms.service.QuestionService;
import com.alikmndlu.oqms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

    @DeleteMapping("/teacher/msq/delete/{question-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteQuestion(@PathVariable("question-id") Long questionId) {
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
}
