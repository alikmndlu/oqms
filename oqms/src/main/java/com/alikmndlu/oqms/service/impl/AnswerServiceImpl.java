package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.dto.AnswerTextDto;
import com.alikmndlu.oqms.model.Answer;
import com.alikmndlu.oqms.model.MultiSelectQuestion;
import com.alikmndlu.oqms.repository.AnswerRepository;
import com.alikmndlu.oqms.service.AnswerService;
import com.alikmndlu.oqms.service.MultiSelectQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AnswerServiceImpl extends BaseServiceImpl<Answer, Long, AnswerRepository>
        implements AnswerService {

    private final AnswerRepository answerRepository;

    private final MultiSelectQuestionService multiSelectQuestionService;

    public AnswerServiceImpl(AnswerRepository answerRepository, MultiSelectQuestionService multiSelectQuestionService) {
        super(answerRepository);
        this.answerRepository = answerRepository;
        this.multiSelectQuestionService = multiSelectQuestionService;
    }

    @Override
    public void insertAnswer(Long questionId, AnswerTextDto answerDto) {
        MultiSelectQuestion multiSelectQuestion = multiSelectQuestionService.findById(questionId).get();
        save(new Answer(
                        answerDto.getText(),
                        multiSelectQuestion
                )
        );
    }

    @Override
    public List<Answer> findAllByQuestionId(Long questionId) {
        return answerRepository.findAllByQuestionId(questionId);
    }

    @Override
    public void updateAnswer(Long answerId, AnswerTextDto answerDto) {
        // Find Answer By ID
        Answer answer = answerRepository.findById(answerId).get();

        // Replace New Answer Text With Old One
        answer.setText(answerDto.getText());

        // Update And Commit Answer
        repository.save(answer);
    }
}
