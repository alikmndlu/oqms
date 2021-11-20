package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.model.QuizQuestion;

import java.util.List;

public interface QuizQuestionService extends BaseService<QuizQuestion, Long> {
    List<QuizQuestion> findByQuizId(Long quizId);
}
