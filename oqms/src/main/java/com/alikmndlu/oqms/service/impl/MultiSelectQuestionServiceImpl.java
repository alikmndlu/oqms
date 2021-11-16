package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.dto.MultiSelectQuestionTitleTextDto;
import com.alikmndlu.oqms.dto.QuestionTrueAnswerDto;
import com.alikmndlu.oqms.model.MultiSelectQuestion;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.repository.MultiSelectQuestionRepository;
import com.alikmndlu.oqms.service.MultiSelectQuestionService;
import com.alikmndlu.oqms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class MultiSelectQuestionServiceImpl extends BaseServiceImpl<MultiSelectQuestion, Long, MultiSelectQuestionRepository>
        implements MultiSelectQuestionService {

    private final MultiSelectQuestionRepository multiSelectQuestionRepository;


    private final UserService userService;

    public MultiSelectQuestionServiceImpl(MultiSelectQuestionRepository multiSelectQuestionRepository, UserService userService) {
        super(multiSelectQuestionRepository);
        this.multiSelectQuestionRepository = multiSelectQuestionRepository;
        this.userService = userService;
    }

    @Override
    public void insertMultiSelectQuestion(MultiSelectQuestionTitleTextDto questionDto) {
        String loggedInTeacherUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User teacher = userService.findByUsername(loggedInTeacherUsername).get();
        multiSelectQuestionRepository.save(
                new MultiSelectQuestion(
                        questionDto.getTitle(),
                        questionDto.getText(),
                        teacher
                )
        );
    }

    @Override
    public void attachTrueAnswerToQuestion(QuestionTrueAnswerDto dto) {
        // Find Question
//        MultiSelectQuestion multiSelectQuestion =
//                multiSelectQuestionRepository.findById(dto.getMultiSelectQuestionId()).get();
//
//        // Find Answer
//        Answer trueAnswer = answerService.findById(dto.getAnswerId()).get();
//
//        // Assign TrueAnswer To Question
//        multiSelectQuestion.setTrueAnswer(trueAnswer);
    }

    @Override
    public void updateQuestion(MultiSelectQuestionTitleTextDto questionDto, Long questionId) {
        // Fetch Question
        MultiSelectQuestion question = findById(questionId).get();

        // Replace New Text And Title
        question.setText(questionDto.getText());
        question.setTitle(questionDto.getTitle());

        // Update Question
        save(question);
    }
}
