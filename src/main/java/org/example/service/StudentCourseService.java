package org.example.service;

import org.example.model.Attendance;
import org.example.model.StudentCourse;
import org.example.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;

    @Autowired
    public StudentCourseService(StudentCourseRepository studentCourseRepository) {
        this.studentCourseRepository = studentCourseRepository;
    }

    public List<StudentCourse> getAllById(int courseId){
        return studentCourseRepository.findAllByCourseCourseID(courseId);
    }

    public StudentCourse addNote(int studentId, int courseId, double nota){
        if(nota > 0 && nota <= 10) {
            StudentCourse studentCourse = studentCourseRepository.findByCourseCourseIDAndStudentStudentID(studentId, courseId);
            if(studentCourse != null) {
                studentCourse.setNote(nota);
                return studentCourseRepository.save(studentCourse);
            }
        }
        return null;
    }

    public StudentCourse addAttendance(int studentId, int courseId, Attendance attendance){
        StudentCourse studentCourse = studentCourseRepository.findByCourseCourseIDAndStudentStudentID(studentId, courseId);

        if(studentCourse != null) {
            studentCourse.setAttendance(attendance);
            return studentCourseRepository.save(studentCourse);
        }
        return null;
    }
}
