package com.alikmndlu.oqms.repository;

import com.alikmndlu.oqms.model.Question;
import com.alikmndlu.oqms.model.Quiz;
import com.alikmndlu.oqms.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

//    @Query("from QuizQuestion qq where qq.quiz.id = ?1")
    List<QuizQuestion> findByQuiz(Quiz quiz);

    @Transactional
    @Modifying
    @Query("delete from QuizQuestion qq where qq.question.id = ?1")
    void deleteByQuestionId(Long questionId);

    @Query("select case when count(qq)> 0 then true else false end from QuizQuestion qq where qq.quiz.id = ?1 and qq.question.id = ?2")
    Boolean isQuestionAddedToQuiz(Long quizId, Long questionId);

    @Transactional
    @Modifying
    @Query("delete from QuizQuestion qq where qq.quiz.id = ?1 and qq.question.id = ?2")
    void deleteQuestionFromQuiz(Long quizId, Long questionId);
}
