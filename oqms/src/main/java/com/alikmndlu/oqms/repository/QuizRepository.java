package com.alikmndlu.oqms.repository;

import com.alikmndlu.oqms.model.Quiz;
import com.alikmndlu.oqms.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
