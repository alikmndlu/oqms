package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.dto.*;
import com.alikmndlu.oqms.model.Question;

import java.util.List;
import java.util.Map;

public interface QuestionService extends BaseService<Question, Long> {

    void insertMultiSelectQuestion(MultiSelectQuestionTitleTextDto questionDto);

    void updateQuestion(MultiSelectQuestionTitleTextDto questionDto, Long questionId);

    List<Question> findByTeacherId(Long teacherId);

    void insertDescriptiveQuestion(QuestionTitleTextDto questionDto);

    void insertMultiSelectQuestion(QuestionTitleTextAnswersTrueAnswerDto questionDto);

    List<Question> getTeacherQuestionsFromBankOfQuestion();

    Map<String, Object> questionObjectToQuestionDto(Question question);

    void deleteQuestionById(Long questionId);

    void updateDescriptiveQuestion(QuestionIdTitleTextDto questionDto);

    void updateMultiSelectQuestion(QuestionIdTitleTextAnswersTrueAnswerDto questionDto);

}
