package com.alikmndlu.oqms.repository;

import com.alikmndlu.oqms.model.MultiSelectQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultiSelectQuestionRepository extends JpaRepository<MultiSelectQuestion, Long> {

}
