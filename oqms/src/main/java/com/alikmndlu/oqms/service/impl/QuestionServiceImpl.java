package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.dto.*;
import com.alikmndlu.oqms.model.Answer;
import com.alikmndlu.oqms.model.Question;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.model.enums.QuestionType;
import com.alikmndlu.oqms.repository.AnswerRepository;
import com.alikmndlu.oqms.repository.QuestionRepository;
import com.alikmndlu.oqms.service.QuestionService;
import com.alikmndlu.oqms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class QuestionServiceImpl extends BaseServiceImpl<Question, Long, QuestionRepository>
        implements QuestionService {

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final UserService userService;

    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository, UserService userService) {
        super(questionRepository);
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
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

    @Override
    public void insertDescriptiveQuestion(QuestionTitleTextDto questionDto) {
        String loggedInTeacherUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User teacher = userService.findByUsername(loggedInTeacherUsername).get();

        questionRepository.save(new Question(
                questionDto.getTitle(),
                questionDto.getText(),
                teacher,
                new ArrayList<>(),
                null
        ));
        log.info("New Descriptive Question Create By {}", loggedInTeacherUsername);
    }

    @Override
    public void insertMultiSelectQuestion(QuestionTitleTextAnswersTrueAnswerDto questionDto) {
        String loggedInTeacherUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User teacher = userService.findByUsername(loggedInTeacherUsername).get();

        Question question = new Question(
                questionDto.getTitle(),
                questionDto.getText(),
                teacher,
                new ArrayList<>(),
                null
        );

        List<Answer> answers = new ArrayList<>();
        questionDto.getAnswers().forEach(answerString -> {
            Answer answerObj = new Answer(answerString, question);
            answerRepository.save(answerObj);
            answers.add(answerObj);
        });
        question.setAnswers(answers);

        Answer trueAnswer = new Answer(questionDto.getTrueAnswer(), question);
        answerRepository.save(trueAnswer);
        question.setTrueAnswer(trueAnswer);

        questionRepository.save(question);
    }

    @Override
    public List<Question> getTeacherQuestionsFromBankOfQuestion() {
        String loggedInTeacherUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User teacher = userService.findByUsername(loggedInTeacherUsername).get();
        return repository.getTeacherQuestionsFromBankOfQuestion(teacher.getId());
    }

    @Override
    public Map<String, Object> questionObjectToQuestionDto(Question question) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", question.getId());
        map.put("type", question.getType().name());
        if (question.getType().equals(QuestionType.MULTI_SELECT)) {
            map.put("answers", question.getAnswers().stream().map(Answer::getText).collect(Collectors.toList()));
            map.put("trueAnswer", question.getTrueAnswer().getText());
        }
        map.put("title", question.getTitle());
        map.put("text", question.getText());
        return map;
    }

    @Override
    public void deleteQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId).get();
        question.setTrueAnswer(null);
        questionRepository.save(question);
        answerRepository.deleteById(question.getTrueAnswer().getId());
        question.getAnswers().forEach(q ->
                answerRepository.deleteById(q.getId())
        );
        questionRepository.delete(question);
    }

    @Override
    public void updateDescriptiveQuestion(QuestionIdTitleTextDto questionDto) {
        Question question = questionRepository.findById(questionDto.getId()).get();
        question.setTitle(questionDto.getTitle());
        question.setText(questionDto.getText());
        questionRepository.save(question);
    }

    @Override
    public void updateMultiSelectQuestion(QuestionIdTitleTextAnswersTrueAnswerDto questionDto) {
        Question question = questionRepository.findById(questionDto.getId()).get();

        question.setTitle(questionDto.getTitle());
        question.setText(questionDto.getText());

        Long oldTrueAnswerId = question.getTrueAnswer().getId();
        question.setTrueAnswer(null);
        answerRepository.deleteById(oldTrueAnswerId);
        answerRepository.deleteAll(question.getAnswers());

        Answer trueAnswer = new Answer(questionDto.getTrueAnswer(), question);
        question.setTrueAnswer(trueAnswer);

        List<Answer> answers = new ArrayList<>();
        questionDto.getAnswers().forEach(answer -> {
                    Answer tempAnswer = new Answer(answer, question);
                    answers.add(tempAnswer);
                }
        );
        question.setAnswers(answers);
    }
}
