package com.alikmndlu.oqms.repository;

import com.alikmndlu.oqms.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacherId(Long id);
}
