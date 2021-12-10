package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.model.ExamResult;
import com.alikmndlu.oqms.model.Quiz;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.repository.ExamResultRepository;
import com.alikmndlu.oqms.service.ExamResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ExamResultServiceImpl extends BaseServiceImpl<ExamResult, Long, ExamResultRepository>
        implements ExamResultService {

    private final ExamResultRepository repository;

    public ExamResultServiceImpl(ExamResultRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Boolean isStudentEnterToQuizBefore(User student, Quiz quiz) {
        return repository.isStudentEnterToQuizBefore(student.getId(), quiz.getId());
    }

    @Override
    public Boolean isExistsByQuizIdAndStudentId(User student, Quiz quiz) {
        return repository.isExistsByQuizIdAndStudentId(student.getId(), quiz.getId());
    }

    @Override
    public Optional<ExamResult> findByQuizIdAndStudentId(Long quizId, Long studentId) {
        return repository.findByQuizIdAndStudentId(quizId, studentId);
    }

    @Override
    public List<ExamResult> findStudentsThatAnswerToQuiz(Long quizId) {
        return repository.findStudentsThatAnswerToQuiz(quizId);
    }
}
