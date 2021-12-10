package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.model.ExamAnswer;
import com.alikmndlu.oqms.repository.ExamAnswerRepository;
import com.alikmndlu.oqms.service.ExamAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Configurable
@Transactional
@Slf4j
public class ExamAnswerServiceImpl extends BaseServiceImpl<ExamAnswer, Long, ExamAnswerRepository>
        implements ExamAnswerService {

    private final ExamAnswerRepository repository;

    public ExamAnswerServiceImpl(ExamAnswerRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<ExamAnswer> findByQuizIdAndStudentId(Long quizId, Long studentID) {
        return repository.findByQuizIdAndStudentId(quizId, studentID);
    }
}
