package org.example.service;

import org.example.model.Attendance;
import org.example.model.StudentCourse;
import org.example.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    private AttendanceRepository attendanceRepository;


    public List<StudentCourse> getAllStudentCourse(){

        return attendanceRepository.findAll();
    }

    public StudentCourse addNote(StudentCourse studentCourse, double nota){
        if(nota > 0 && nota <= 10) {
            studentCourse.setNote(nota);
        }
        return attendanceRepository.save(studentCourse);
    }

    public StudentCourse addAttendance(StudentCourse studentCourse, Attendance attendance){
        studentCourse.setAttendance(attendance);
        return attendanceRepository.save(studentCourse);
    }
}
