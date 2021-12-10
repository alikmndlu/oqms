package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.model.Question;
import com.alikmndlu.oqms.model.Quiz;
import com.alikmndlu.oqms.model.QuizQuestion;

import java.util.List;

public interface QuizQuestionService extends BaseService<QuizQuestion, Long> {
    List<QuizQuestion> findByQuiz(Quiz quiz);

    void deleteByQuestionId(Long questionId);

    Boolean isQuestionAddedToQuiz(Long quizId, Long questionId);

    void addQuestionToQuiz(Long quizId, Long questionId, Long score);

    void deleteQuestionFromQuiz(Long quizId, Long questionId);
}
