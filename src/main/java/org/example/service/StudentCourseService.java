package org.example.service;

import org.example.model.Attendance;
import org.example.model.StudentCourse;
import org.example.repository.StudentCourseServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {

    private final StudentCourseServiceRepository studentCourseServiceRepository;

    @Autowired
    public StudentCourseService(StudentCourseServiceRepository studentCourseServiceRepository) {
        this.studentCourseServiceRepository = studentCourseServiceRepository;
    }

    public List<StudentCourse> getByStudentCourse(int courseId){
        return studentCourseServiceRepository.findAllByCourseCourseID(courseId);
    }

    public StudentCourse addNote(int studentId, int courseId, double nota){
        StudentCourse studentCourse = studentCourseServiceRepository.findByCourseCourseIDAndStudentStudentID(studentId, courseId);
        if(nota > 0 && nota <= 10 && studentCourse != null) {
            studentCourse.setNote(nota);
            return studentCourseServiceRepository.save(studentCourse);
        }
        return null;
    }

    public StudentCourse addAttendance(int studentId, int courseId, Attendance attendance){
        StudentCourse studentCourse = studentCourseServiceRepository.findByCourseCourseIDAndStudentStudentID(studentId, courseId);

        if(studentCourse != null) {
            studentCourse.setAttendance(attendance);
            return studentCourseServiceRepository.save(studentCourse);
        }
        return null;
    }
}
