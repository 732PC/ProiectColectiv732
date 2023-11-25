package org.example.Service;

import jakarta.transaction.Transactional;
import org.example.Model.Course;
import org.example.Model.Enrollment;
import org.example.Model.Students;
import org.example.Repository.courseRepo;
import org.example.Repository.studentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private courseRepo courseRepository;

    @Autowired
    private studentRepo studentsRepository;

    @Transactional
    public void assignRequiredCoursesToStudentForStudyYear(int studentId, int studyYear) {
        Students student = studentsRepository.findById(studentId).orElse(null);

        if (student != null) {

            List<Course> requiredCourses = courseRepository.findRequiredCoursesByStudyYear(studyYear);


            assignCoursesToStudentAvoidOverallocation(student, requiredCourses);
        }
    }

    @Transactional
    public void assignRequiredCoursesToStudentAutomatically(int studentId) {
        Students student = studentsRepository.findById(studentId).orElse(null);

        if (student != null) {

            int currentStudyYear = student.getStudyYear();


            List<Course> requiredCourses = courseRepository.findRequiredCoursesByStudyYear(currentStudyYear);


            assignCoursesToStudentAvoidOverallocation(student, requiredCourses);
        }
    }

    private void assignCoursesToStudentAvoidOverallocation(Students student, List<Course> courses) {

        student.getEnrollments().clear();


        for (Course course : courses) {
            boolean isAlreadyEnrolled = student.getEnrollments().stream()
                    .anyMatch(enrollment -> enrollment.getCourse().equals(course));

            if (!isAlreadyEnrolled) {
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                student.getEnrollments().add(enrollment);
            }
        }


        studentsRepository.save(student);
    }
}
