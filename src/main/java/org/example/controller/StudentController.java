package org.example.controller;


import jakarta.persistence.EntityNotFoundException;
import org.example.model.StudentCourse;
import org.example.model.Students;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Students> getAllStudents(){
        return studentService.getAllStudents();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Students> getStudentById(@PathVariable Integer id){
        Students student = studentService.getStudentById(id).orElse(null);
        return (student!=null) ? ResponseEntity.ok(student):ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}/courses")
    public ResponseEntity<Set<StudentCourse>> getCoursesForStudent(@PathVariable Integer id) {
        Students student = studentService.getStudentById(id).orElse(null);

        if (student != null) {
            Set<StudentCourse> studentCourses = student.getStudentCourses();

            if (studentCourses != null) {
                return ResponseEntity.ok(studentCourses);
            } else {
                return ResponseEntity.ok(Collections.emptySet());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Students student) {
        {
            Students addedStudent = studentService.addStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedStudent);

        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
