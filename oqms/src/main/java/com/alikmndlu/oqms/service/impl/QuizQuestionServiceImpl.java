package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.model.QuizQuestion;
import com.alikmndlu.oqms.repository.QuizQuestionRepository;
import com.alikmndlu.oqms.service.QuizQuestionService;
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

    public QuizQuestionServiceImpl(QuizQuestionRepository repository, QuizQuestionRepository repository1) {
        super(repository);
        this.repository = repository1;
    }

    @Override
    public List<QuizQuestion> findByQuizId(Long quizId) {
        return repository.findByQuizId(quizId);
    }
}
