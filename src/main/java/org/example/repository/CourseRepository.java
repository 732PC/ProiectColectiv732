package org.example.repository;

import org.example.model.Course;
import org.example.model.Professors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    @Query("SELECT c.professor FROM Course c WHERE c.courseID = :courseId")
    Professors findProfessorByCourseId(@Param("courseId") Integer courseId);

    @Query(value = "SELECT c.courseName, c.type, p.firstname, p.lastname " +
            "FROM courses c " +
            "JOIN professors p ON c.ProfessorID = p.professorID " +
            "WHERE c.type = 'optional'", nativeQuery = true)
    List<Object[]> findOptionalCourses();

    @Query(value = "SELECT c.courseName, c.type, p.firstname, p.lastname " +
            "FROM courses c " +
            "JOIN professors p ON c.ProfessorID = p.professorID " +
            "JOIN studentcourse sc ON sc.courseFK = c.courseID " +
            "JOIN students s ON cs.studentFK = s.studentID " +
            "WHERE s.email = :email", nativeQuery = true)
    List<Object[]> findEnrolledCoursesOfStudent(String email);


    Course findByName(String name);
}
