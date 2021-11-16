package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.MultiSelectQuestionTitleTextDto;
import com.alikmndlu.oqms.service.MultiSelectQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class MultiSelectQuestionController {

    private final MultiSelectQuestionService multiSelectQuestionService;

//    private final  AnswerService answerService;

    @PostMapping("teacher/multi-select-question/create")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void insertMultiSelectQuestion(@RequestBody MultiSelectQuestionTitleTextDto questionDto){
        multiSelectQuestionService.insertMultiSelectQuestion(questionDto);
    }

//    @PutMapping("teacher/question/set-true-answer")
//    @PreAuthorize("hasRole('ROLE_TEACHER')")
//    public void attachTrueAnswerToQuestion(@RequestBody QuestionTrueAnswerDto dto){
//        multiSelectQuestionService.attachTrueAnswerToQuestion(dto);
//    }
//
//    @PutMapping("teacher/question/add-answer")
//    @PreAuthorize("hasRole('ROLE_TEACHER')")
//    public void addAnswerToQuestion(@RequestBody QuestionAddAnswerDto dto){
////        answerService.addAnswer(dto);
//    }
}
