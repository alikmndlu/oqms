package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.model.ExamAnswer;

import java.util.List;

public interface ExamAnswerService extends BaseService<ExamAnswer, Long> {

    List<ExamAnswer> findByQuizIdAndStudentId(Long quizId, Long studentID);
}
