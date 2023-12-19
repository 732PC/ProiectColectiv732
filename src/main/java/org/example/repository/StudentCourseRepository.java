package org.example.repository;

import org.example.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {

    List<StudentCourse> findAllByCourse_CourseID(int courseId);

    StudentCourse findByCourseCourseIDAndStudentStudentID(int studentFk, int courseId);
}
