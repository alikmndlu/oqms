package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.dto.AnswerTextDto;
import com.alikmndlu.oqms.model.Answer;
import com.alikmndlu.oqms.model.Question;
import com.alikmndlu.oqms.repository.AnswerRepository;
import com.alikmndlu.oqms.service.AnswerService;
import com.alikmndlu.oqms.service.QuestionService;
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

    private final QuestionService questionService;

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionService questionService) {
        super(answerRepository);
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    @Override
    public void insertAnswer(Long questionId, AnswerTextDto answerDto) {
        Question question = questionService.findById(questionId).get();
        save(new Answer(
                        answerDto.getText(),
                question
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

    @Override
    public void attachTrueAnswerToQuestion(Long questionId, Long answerId) {
        // Find Question
        Question question = questionService.findById(questionId).get();

        // Find Answer
        Answer trueAnswer = answerRepository.findById(answerId).get();

        // Assign TrueAnswer To Question
        question.setTrueAnswer(trueAnswer);
    }
}
