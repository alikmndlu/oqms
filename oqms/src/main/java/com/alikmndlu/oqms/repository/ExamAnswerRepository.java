package com.alikmndlu.oqms.repository;

import com.alikmndlu.oqms.model.ExamAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Long> {

    @Query("from ExamAnswer ea where ea.quizQuestion.quiz.id = ?1 and ea.student.id = ?2")
    List<ExamAnswer> findByQuizIdAndStudentId(Long quizId, Long studentID);
}
