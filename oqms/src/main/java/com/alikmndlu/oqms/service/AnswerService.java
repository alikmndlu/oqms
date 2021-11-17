package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.dto.AnswerTextDto;
import com.alikmndlu.oqms.model.Answer;

import java.util.List;

public interface AnswerService extends BaseService<Answer, Long> {

    void insertAnswer(Long questionId, AnswerTextDto answerDto);

    List<Answer> findAllByQuestionId(Long questionId);

    void updateAnswer(Long answerId, AnswerTextDto answerDto);
}
