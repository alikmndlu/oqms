package com.alikmndlu.oqms.repository;

import com.alikmndlu.oqms.model.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    @Query("select case when(count(er) = 0) then false when(er.isFinished = false) then false else true end from ExamResult er where er.student.id = ?1 and er.quiz.id = ?2")
    Boolean isStudentEnterToQuizBefore(Long studentId, Long quizId);

    @Query("select case when(count(er) = 0) then false else true end from ExamResult er where er.student.id = ?1 and er.quiz.id = ?2")
    Boolean isExistsByQuizIdAndStudentId(Long studentId, Long quizId);

    @Query("from ExamResult er where er.quiz.id=?1 and er.student.id=?2")
    Optional<ExamResult> findByQuizIdAndStudentId(Long quizId, Long studentId);

    @Query("from ExamResult er where er.quiz.id = ?1 and er.isFinished = true")
    List<ExamResult> findStudentsThatAnswerToQuiz(Long quizId);
}
