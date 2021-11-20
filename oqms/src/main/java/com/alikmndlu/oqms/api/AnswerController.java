package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.AnswerIdTextDto;
import com.alikmndlu.oqms.dto.AnswerTextDto;
import com.alikmndlu.oqms.model.Answer;
import com.alikmndlu.oqms.service.AnswerService;
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
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/teacher/question/{question-id}/answer/create")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void createAnswer(@PathVariable("question-id") Long questionId, @RequestBody AnswerTextDto answerDto) {
        answerService.insertAnswer(questionId, answerDto);
    }

    @GetMapping("/teacher/question/{question-id}/answers")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<List<AnswerIdTextDto>> getQuestionAnswers(@PathVariable("question-id") Long questionId) {
        return ResponseEntity.ok().body(
                AnswerIdTextDto.AnswerListToAnswerIdTextDtoList(answerService.findAllByQuestionId(questionId))
        );
    }

    @PutMapping("/teacher/answer/update/{answer-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void updateAnswer(@PathVariable("answer-id") Long answerId, @RequestBody AnswerTextDto answerDto) {
        answerService.updateAnswer(answerId, answerDto);
    }

    @DeleteMapping("/teacher/answer/delete/{answer-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteAnswer(@PathVariable("answer-id") Long answerId) {
        answerService.deleteById(answerId);
    }
}
