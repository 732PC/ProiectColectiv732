package org.example.Service;

import jakarta.transaction.Transactional;
import org.example.Model.Course;
import org.example.Model.Enrollment;
import org.example.Model.Students;
import org.example.Repository.courseRepo;
import org.example.Repository.studentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EnrollmentService {

    @Autowired
    private courseRepo courseRepository;

    @Autowired
    private studentRepo studentsRepository;

    @Transactional
    public void assignRequiredCoursesToStudentForStudyYear( int studyYear) {
        List<Students> studentsInYear = studentsRepository.findRequiredStudentsByStudyYear(studyYear);
        for (Students student : studentsInYear) {
            List<Course> requiredCourses = courseRepository.findRequiredCoursesByStudyYear(studyYear);
            assignCoursesToStudentAvoidOverallocation(student, requiredCourses);
        }

    }

    @Transactional
    public void assignRequiredCoursesToStudentAutomatically(int studentId, int courseId) {
        Students student = studentsRepository.findById(studentId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

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
            }//
        }


        studentsRepository.save(student);
    }

    public List<Students> getStudents(){
        return studentsRepository.findAll();

    }

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public Set<Integer> getAllStudyYears() {
        Set<Integer> studyYears = new HashSet<>();

        List<Students> allStudents = studentsRepository.findAll();

        for (Students student : allStudents) {
            studyYears.add(student.getStudyYear());
        }

        return studyYears;
    }


}



