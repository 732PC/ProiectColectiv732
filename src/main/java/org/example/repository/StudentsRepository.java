package org.example.repository;


import org.example.model.Students;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentsRepository extends JpaRepository<Students,Integer> {
    List<Students> findRequiredStudentsByStudyYear(int studyYear);
}
