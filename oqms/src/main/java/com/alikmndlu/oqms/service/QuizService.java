package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.dto.QuizTitleInfoTimeCourseIdDto;
import com.alikmndlu.oqms.model.Quiz;

public interface QuizService extends BaseService<Quiz, Long> {

    void addQuizForCourse(QuizTitleInfoTimeCourseIdDto quizDto);
}
