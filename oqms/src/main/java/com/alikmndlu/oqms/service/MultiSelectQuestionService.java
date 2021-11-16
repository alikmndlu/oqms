package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.dto.MultiSelectQuestionTitleTextDto;
import com.alikmndlu.oqms.dto.MultiSelectQuestionTitleTextQuizIdDto;
import com.alikmndlu.oqms.dto.QuestionTrueAnswerDto;
import com.alikmndlu.oqms.model.MultiSelectQuestion;

public interface MultiSelectQuestionService extends BaseService<MultiSelectQuestion, Long> {


    void insertMultiSelectQuestion(MultiSelectQuestionTitleTextDto questionDto);

    void attachTrueAnswerToQuestion(QuestionTrueAnswerDto dto);
}
