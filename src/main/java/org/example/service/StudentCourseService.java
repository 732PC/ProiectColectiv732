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
        return studentCourseRepository.findAllByCourse_CourseID(courseId);
    }

    public StudentCourse addNote(int studentId, int courseId, double nota) {
        StudentCourse studentCourse = studentCourseRepository
                .findByStudentCourseID_StudentIDAndStudentCourseID_CourseID(studentId, courseId);

        if (studentCourse == null) {
            return null;
        } else {
            studentCourse.setNote(nota);
            return studentCourseRepository.save(studentCourse);
        }
    }


    public StudentCourse addAttendance(int studentId, int courseId, Attendance attendance){
        StudentCourse studentCourse = studentCourseRepository
                .findByStudentCourseID_StudentIDAndStudentCourseID_CourseID(studentId, courseId);

        if (studentCourse == null) {
            return null;
        } else {
            studentCourse.setAttendance(attendance);
            return studentCourseRepository.save(studentCourse);
        }
    }
}
