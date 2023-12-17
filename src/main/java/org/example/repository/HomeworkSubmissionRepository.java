package org.example.repository;

import org.example.model.HomeworkSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface HomeworkSubmissionRepository extends JpaRepository<HomeworkSubmission, Long> {
}
