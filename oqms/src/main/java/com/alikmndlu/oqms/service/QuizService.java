package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.dto.QuizIdTitleInfoTimeDto;
import com.alikmndlu.oqms.dto.QuizTitleInfoTimeCourseIdDto;
import com.alikmndlu.oqms.model.Question;
import com.alikmndlu.oqms.model.Quiz;

import java.util.List;


public interface QuizService extends BaseService<Quiz, Long> {

    void addQuizForCourse(QuizTitleInfoTimeCourseIdDto quizDto);

    List<Quiz> findByCourseId(Long courseId);

    void updateQuiz(QuizIdTitleInfoTimeDto quizzDto);

}
