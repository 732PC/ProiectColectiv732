package org.example.Service;

import jakarta.transaction.Transactional;
import org.example.Model.Course;
import org.example.Model.Enrollment;
import org.example.Model.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {


    private org.example.Repository.courseRepository courseRepository;


    private org.example.Repository.studentsRepository studentsRepository;

    @Autowired
    public EnrollmentService(org.example.Repository.courseRepository courseRepository, org.example.Repository.studentsRepository studentsRepository) {
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
    public void assignRequiredCoursesToStudentAutomatically() {
        List<Students> allStudents = studentsRepository.findAll();
        List<Course> allCourses = courseRepository.findAll();

        allStudents.forEach(student -> {
            if (student.getStudyYear() != null) {
                List<Course> requiredCourses = allCourses.stream()
                        .filter(course -> student.getStudyYear().equals(course.getStudyYear()))
                        .flatMap(course -> courseRepository.findRequiredCoursesByStudyYear(course.getStudyYear()).stream())
                        .collect(Collectors.toList());

                assignCoursesToStudentAvoidOverallocation(student, requiredCourses);
            }
        });
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



