package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.MultiSelectQuestionTitleTextDto;
import com.alikmndlu.oqms.service.MultiSelectQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MultiSelectQuestionController {

    private final MultiSelectQuestionService multiSelectQuestionService;


    @PostMapping("teacher/msq/create")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void insertMultiSelectQuestion(@RequestBody MultiSelectQuestionTitleTextDto questionDto){
        multiSelectQuestionService.insertMultiSelectQuestion(questionDto);
    }

    @PutMapping("teacher/msq/update/{question-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void updateTitleAndTextQuestion(@RequestBody MultiSelectQuestionTitleTextDto questionDto, @PathVariable("question-id") Long questionId){
        multiSelectQuestionService.updateQuestion(questionDto, questionId);
    }

    @DeleteMapping("teacher/msq/delete/{question-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteQuestion(@PathVariable("question-id") Long questionId){
        multiSelectQuestionService.deleteById(questionId);
    }
}
