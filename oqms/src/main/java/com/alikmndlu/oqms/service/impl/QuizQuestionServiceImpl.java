package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.model.Question;
import com.alikmndlu.oqms.model.Quiz;
import com.alikmndlu.oqms.model.QuizQuestion;
import com.alikmndlu.oqms.repository.QuizQuestionRepository;
import com.alikmndlu.oqms.service.QuestionService;
import com.alikmndlu.oqms.service.QuizQuestionService;
import com.alikmndlu.oqms.service.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class QuizQuestionServiceImpl extends BaseServiceImpl<QuizQuestion, Long, QuizQuestionRepository>
        implements QuizQuestionService {

    private final QuizQuestionRepository repository;

    private final QuizService quizService;

    private final QuestionService questionService;

    public QuizQuestionServiceImpl(QuizQuestionRepository repository, QuizService quizService, QuestionService questionService) {
        super(repository);
        this.repository = repository;
        this.quizService = quizService;
        this.questionService = questionService;
    }

    @Override
    public List<QuizQuestion> findByQuiz(Quiz quiz) {
        return repository.findByQuiz(quiz);
    }

    @Override
    public void deleteByQuestionId(Long questionId) {
        repository.deleteByQuestionId(questionId);
    }

    @Override
    public Boolean isQuestionAddedToQuiz(Long quizId, Long questionId) {
        return repository.isQuestionAddedToQuiz(quizId, questionId);
    }

    @Override
    public void addQuestionToQuiz(Long quizId, Long questionId, Long score) {
        Quiz quiz = quizService.findById(quizId).get();
        Question question = questionService.findById(questionId).get();
        save(new QuizQuestion(quiz, question, score));
        log.info("Question {} With Score {}, Added To Quiz {}", questionId, score, quizId);
    }

    @Override
    public void deleteQuestionFromQuiz(Long quizId, Long questionId) {
        repository.deleteQuestionFromQuiz(quizId, questionId);
    }

}
