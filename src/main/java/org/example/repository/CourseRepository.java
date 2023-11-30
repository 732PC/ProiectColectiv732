package org.example.repository;

import org.example.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query(value = "SELECT c.course_name, c.is_optional, u.firstname, u.lastname, cs.day_of_week, cs.time " +
            "FROM courses c " +
            "JOIN teachers t ON c.teacher_id = t.id " +
            "JOIN users u ON t.user_id = u.id " +
            "JOIN course_schedule cs ON c.id = cs.course_id " +
            "WHERE c.is_optional = TRUE", nativeQuery = true)
    List<Object[]> findOptionalCourses();

    @Query(value = "SELECT c.course_name, c.is_optional, ut.firstname, ut.lastname, cs.day_of_week, cs.time " +
            "FROM courses c " +
            "JOIN teachers t ON c.teacher_id = t.id " +
            "JOIN users ut ON t.user_id = ut.id " +
            "JOIN student_courses sc ON sc.course_id = c.id " +
            "JOIN students s ON s.id = sc.student_id " +
            "JOIN users us ON us.id = s.id " +
            "JOIN course_schedule cs ON c.id = cs.course_id " +
            "WHERE us.email = :email", nativeQuery = true)
    List<Object[]> findEnrolledCoursesOfStudent(String email);

    Course findByCourseName(String courseName);
}
