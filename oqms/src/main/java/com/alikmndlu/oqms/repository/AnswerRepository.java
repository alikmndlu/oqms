package com.alikmndlu.oqms.repository;

import com.alikmndlu.oqms.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("from Answer a where a.question.id = ?1")
    List<Answer> findAllByQuestionId(Long questionId);
}
