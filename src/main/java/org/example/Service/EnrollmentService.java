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


    private courseRepo courseRepository;


    private studentRepo studentsRepository;

    @Autowired
    public EnrollmentService(courseRepo courseRepository, studentRepo studentsRepository) {
        this.courseRepository = courseRepository;
        this.studentsRepository = studentsRepository;
    }

    @Transactional
    public void assignRequiredCoursesToStudentForStudyYear( int studyYear) {
        List<Students> studentsInYear = studentsRepository.findRequiredStudentsByStudyYear(studyYear);
        for (Students student : studentsInYear) {
            List<Course> requiredCourses = courseRepository.findRequiredCoursesByStudyYear(studyYear);
            assignCoursesToStudentAvoidOverallocation(student, requiredCourses);
        }

    }

    @Transactional
    public void assignRequiredCoursesToStudentAutomatically(int studyYear) {
        List<Students> allStudents = studentsRepository.findAll();

        for (Students student : allStudents) {
            if (student.getStudyYear() != null && student.getStudyYear() == studyYear) {
                List<Course> requiredCourses = courseRepository.findRequiredCoursesByStudyYear(studyYear);
                assignCoursesToStudentAvoidOverallocation(student, requiredCourses);
            }
        }
    }

     public void assignCoursesToStudentAvoidOverallocation(Students student, List<Course> courses) {

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
///

        studentsRepository.save(student);
    }




}



