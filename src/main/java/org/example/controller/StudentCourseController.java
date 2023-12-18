package org.example.controller;


import org.example.model.Attendance;
import org.example.model.StudentCourse;
import org.example.service.StudentCourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    public StudentCourseController(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }


    @GetMapping(value = "/list")
    public ResponseEntity<List<StudentCourse>> getList(@RequestParam int courseId){
        List<StudentCourse> studentCourseList = this.studentCourseService.getByStudentCourse(courseId);
        
        if(studentCourseList == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
            else{
                return new ResponseEntity<>(studentCourseList, HttpStatus.OK);
        }
    }

    @PostMapping(value="/nota")
    public ResponseEntity<StudentCourse> updateNota(@RequestParam int studentId, @RequestParam int coruseId, @RequestParam double nota){
        StudentCourse updatedNota = this.studentCourseService.addNote(studentId, coruseId, nota);

        if(updatedNota == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
            else{
                return new ResponseEntity<>(updatedNota, HttpStatus.OK);
            }
        }

    @PostMapping(value="/prezenta")
    public ResponseEntity<StudentCourse> updateAttendance(@RequestParam int studentId, @RequestParam int courseId, @RequestParam Attendance attendance){
        StudentCourse updatedAttendance = this.studentCourseService.addAttendance(studentId, courseId, attendance);

        if(updatedAttendance == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        else{
            return new ResponseEntity<>(updatedAttendance, HttpStatus.OK);
        }
    }
}
