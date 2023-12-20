package org.example.controller;


import org.example.model.Attendance;
import org.example.model.StudentCourse;
import org.example.model.StudentCourseRequestDTO;
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


    @GetMapping(value = "/studentCourses")
    public ResponseEntity<List<StudentCourse>> getList(@RequestParam int courseId){
        List<StudentCourse> studentCourseList = this.studentCourseService.getAllById(courseId);
        
        if(studentCourseList == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
            else{
                return new ResponseEntity<>(studentCourseList, HttpStatus.OK);
        }
    }

    @PostMapping("/studentCourses")
    public ResponseEntity<?> updateNotaAndAttendances(@RequestBody List<StudentCourseRequestDTO> studentCourseRequestDTOS) {
        for(StudentCourseRequestDTO studentCourse : studentCourseRequestDTOS){
            this.studentCourseService.addNote(studentCourse.getStudentId(), studentCourse.getCourseId(),
                    studentCourse.getGrade());

            if(studentCourse.isAttendance()){
                this.studentCourseService.addAttendance(studentCourse.getStudentId(),
                        studentCourse.getCourseId(), Attendance.PRZ);
            }
            else {
                this.studentCourseService.addAttendance(studentCourse.getStudentId(),
                        studentCourse.getCourseId(), Attendance.ABS);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
