package com.alikmndlu.oqms.repository;

import com.alikmndlu.oqms.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("from Question q where q.teacher.id = ?1")
    List<Question> findByTeacherId(Long teacherId);
}
