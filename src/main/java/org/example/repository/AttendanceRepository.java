package org.example.repository;

import org.example.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<StudentCourse, Integer> {
    List<StudentCourse> findAll();
}
