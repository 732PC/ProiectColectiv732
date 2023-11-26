package org.example.Repository;

import org.example.Model.Course;
import org.example.Model.Students;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface studentRepo extends JpaRepository<Students,Integer> {
    List<Students> findRequiredStudentsByStudyYear(int studyYear);
}
