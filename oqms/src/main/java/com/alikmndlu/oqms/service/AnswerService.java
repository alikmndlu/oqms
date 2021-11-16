package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.dto.AnswerTextDto;
import com.alikmndlu.oqms.model.Answer;

public interface AnswerService extends BaseService<Answer, Long> {

    void insertAnswer(Long questionId, AnswerTextDto answerDto);
}
