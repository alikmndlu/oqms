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

    @PostMapping("/teacher/msq/{question-id}/answer/create")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void createAnswer(@PathVariable("question-id") Long questionId, @RequestBody AnswerTextDto answerDto) {
        answerService.insertAnswer(questionId, answerDto);
    }

    @GetMapping("/teacher/msq/{question-id}/answers")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<List<AnswerIdTextDto>> createAnswer(@PathVariable("question-id") Long questionId) {
        return ResponseEntity.ok().body(
                AnswerIdTextDto.AnswerListToAnswerIdTextDtoList(answerService.findAllByQuestionId(questionId))
        );
    }
}