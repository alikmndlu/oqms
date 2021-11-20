package com.alikmndlu.oqms.repository;

import com.alikmndlu.oqms.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    @Query("from QuizQuestion qq where qq.quiz.id = ?1")
    List<QuizQuestion> findByQuizId(Long quizId);
}
