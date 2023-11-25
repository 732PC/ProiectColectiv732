package org.example.Repository;

import org.example.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface courseRepo extends JpaRepository<Course,Integer> {
    List<Course> findRequiredCoursesByStudyYear(int studyYear);

}
