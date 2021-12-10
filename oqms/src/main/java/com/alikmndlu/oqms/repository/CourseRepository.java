package com.alikmndlu.oqms.repository;

import com.alikmndlu.oqms.model.Course;
import com.alikmndlu.oqms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacherId(Long id);

    @Query("from Course c where ?1 member of c.students")
    List<Course> getStudentEnrollCourses(User student);
}
