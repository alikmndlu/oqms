package com.alikmndlu.oqms.repository;

import com.alikmndlu.oqms.model.Question;
import com.alikmndlu.oqms.model.Quiz;
import com.alikmndlu.oqms.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("from Quiz q where q.course.id = ?1")
    List<Quiz> findByCourseId(Long courseId);
}
