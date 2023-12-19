package org.example.repository;

import org.example.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    List<Course> findRequiredCoursesByStudyYear(int studyYear);

}
