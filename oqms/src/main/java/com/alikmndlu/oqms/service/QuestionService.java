package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.dto.MultiSelectQuestionTitleTextDto;
import com.alikmndlu.oqms.dto.QuestionTrueAnswerDto;
import com.alikmndlu.oqms.model.Question;

import java.util.List;

public interface QuestionService extends BaseService<Question, Long> {

    void insertMultiSelectQuestion(MultiSelectQuestionTitleTextDto questionDto);

    void updateQuestion(MultiSelectQuestionTitleTextDto questionDto, Long questionId);

    List<Question> findByTeacherId(Long teacherId);
}
