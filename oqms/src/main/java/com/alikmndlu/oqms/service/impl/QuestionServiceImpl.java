package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.dto.MultiSelectQuestionTitleTextDto;
import com.alikmndlu.oqms.model.Answer;
import com.alikmndlu.oqms.model.Question;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.repository.QuestionRepository;
import com.alikmndlu.oqms.service.AnswerService;
import com.alikmndlu.oqms.service.QuestionService;
import com.alikmndlu.oqms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class QuestionServiceImpl extends BaseServiceImpl<Question, Long, QuestionRepository>
        implements QuestionService {

    private final QuestionRepository questionRepository;


    private final UserService userService;

    public QuestionServiceImpl(QuestionRepository questionRepository, UserService userService) {
        super(questionRepository);
        this.questionRepository = questionRepository;
        this.userService = userService;
    }

    @Override
    public void insertMultiSelectQuestion(MultiSelectQuestionTitleTextDto questionDto) {
        String loggedInTeacherUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User teacher = userService.findByUsername(loggedInTeacherUsername).get();
        questionRepository.save(
                new Question(
                        questionDto.getTitle(),
                        questionDto.getText(),
                        teacher
                )
        );
    }

    @Override
    public void updateQuestion(MultiSelectQuestionTitleTextDto questionDto, Long questionId) {
        // Fetch Question
        Question question = findById(questionId).get();

        // Replace New Text And Title
        question.setText(questionDto.getText());
        question.setTitle(questionDto.getTitle());

        // Update Question
        save(question);
    }

    @Override
    public List<Question> findByTeacherId(Long teacherId) {
        return questionRepository.findByTeacherId(teacherId);
    }
}
