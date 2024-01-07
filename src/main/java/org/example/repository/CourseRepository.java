package org.example.repository;

import org.example.model.Course;
import org.example.model.Professors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    @Query("SELECT c.professor FROM Course c WHERE c.courseID = :courseId")
    Professors findProfessorByCourseId(@Param("courseId") Integer courseId);
}
