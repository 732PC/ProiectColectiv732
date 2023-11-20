package org.example.controllers;


import org.example.models.Students;
import org.example.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
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


    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Students student) {
        if (isValidCnpLength(student.getCnp())) {
            Students addedStudent = studentService.addStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedStudent);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid CNP length. CNP must be at least 13 digits.");
        }
    }

    private boolean isValidCnpLength(String cnp) {
        return cnp != null && cnp.length() >= 13;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Students>> updateStudent(@PathVariable Integer id, @RequestBody Students student) {
        Optional<Students> updatedStudent = studentService.updateStudent(id, student);

        if (updatedStudent.isPresent()) {
            return ResponseEntity.ok(updatedStudent);
        } else {
            return ResponseEntity.notFound().build();
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
