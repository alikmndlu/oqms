package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.model.ExamResult;
import com.alikmndlu.oqms.model.Quiz;
import com.alikmndlu.oqms.model.User;

import java.util.List;
import java.util.Optional;

public interface ExamResultService extends BaseService<ExamResult, Long> {

    Boolean isStudentEnterToQuizBefore(User student, Quiz quiz);

    Boolean isExistsByQuizIdAndStudentId(User student, Quiz quiz);

    Optional<ExamResult> findByQuizIdAndStudentId(Long quizId, Long studentId);

    List<ExamResult> findStudentsThatAnswerToQuiz(Long quizId);
}
