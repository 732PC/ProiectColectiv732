package org.example.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.HomeworkSubmission;
import org.example.model.HomeworkSubmissionResponse;
import org.example.model.Students;
import org.example.service.StudentService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllStudents() {
        List<Students> studentsList = Arrays.asList(new Students(), new Students());
        when(studentService.getAllStudents()).thenReturn(studentsList);

        assertEquals(studentsList, studentController.getAllStudents());
    }

    @Test
    void getStudentById() {
        int studentId = 1;
        Students student = new Students();
        when(studentService.getStudentById(studentId)).thenReturn(Optional.of(student));

        ResponseEntity<Students> responseEntity = studentController.getStudentById(studentId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(student, responseEntity.getBody());
    }

    @Test
    void getStudentByIdNotFound() {
        int nonExistingStudentId = 999;
        when(studentService.getStudentById(nonExistingStudentId)).thenReturn(Optional.empty());

        ResponseEntity<Students> responseEntity = studentController.getStudentById(nonExistingStudentId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void addStudent() {
        Students student = new Students();
        student.setFirstname("John");
        student.setLastname("Doe");
        student.setCnp("1234567890123");

        when(studentService.addStudent(anyString(), anyString(), anyString(), any(LocalDate.class), anyInt(), anyString(), anyString(), anyString())).thenReturn(student);

        ResponseEntity<?> responseEntity = studentController.addStudent("John", "Doe", "1234567890123", LocalDate.of(2000, 1, 1), 1, "Bachelor", "Self-funding", "High School");

        verify(studentService, times(1)).addStudent(anyString(), anyString(), anyString(), any(LocalDate.class), anyInt(), anyString(), anyString(), anyString());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(student, responseEntity.getBody());
    }


    @Test
    void updateStudent() {
        int studentId = 3;
        Students updatedStudent = new Students();
        Optional<Students> existingStudent = Optional.of(new Students());

        when(studentService.updateStudent(studentId, updatedStudent)).thenReturn(existingStudent);

        ResponseEntity<Students> responseEntity = studentController.updateStudent(studentId, updatedStudent);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(existingStudent.isPresent());
        assertEquals(existingStudent.get(), responseEntity.getBody());
    }

    @Test
    void updateStudentNotFound() {
        int nonExistingStudentId = 999;
        Students updatedStudent = new Students();

        when(studentService.updateStudent(nonExistingStudentId, updatedStudent)).thenReturn(Optional.empty());

        ResponseEntity<Students> responseEntity = studentController.updateStudent(nonExistingStudentId, updatedStudent);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void deleteStudent() {
        int studentId = 1;

        ResponseEntity<?> responseEntity = studentController.deleteStudent(studentId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(studentService, times(1)).deleteStudent(studentId);
    }

    @Test
    void deleteStudentNotFound() {
        int nonExistingStudentId = 999;

        when(studentService.deleteStudent(nonExistingStudentId)).thenThrow(new EntityNotFoundException("Student not found"));

        ResponseEntity<?> responseEntity = studentController.deleteStudent(nonExistingStudentId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Student not found", responseEntity.getBody());
    }


    @Test
    public void testAddHomeworkSubmission() {
        Integer studentId = 1;
        Map<String, String> requestBody = Collections.singletonMap("homeworkText", "Homework Text");
        when(studentService.addHomeworkSubmission(studentId, "Homework Text")).thenReturn(new HomeworkSubmissionResponse());

        ResponseEntity<?> result = studentController.addHomeworkSubmission(studentId, requestBody);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(studentService, times(1)).addHomeworkSubmission(studentId, "Homework Text");
    }


    @Test
    void testAddHomeworkSubmissionBadRequest() {
        Integer studentId = 1;
        Map<String, String> requestBody = Collections.singletonMap("homeworkText", "Homework Text");

        when(studentService.addHomeworkSubmission(studentId, "Homework Text"))
                .thenThrow(new IllegalArgumentException("Invalid request"));

        ResponseEntity<?> result = studentController.addHomeworkSubmission(studentId, requestBody);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());

        verify(studentService, times(1)).addHomeworkSubmission(studentId, "Homework Text");
    }


    @Test
    public void testDeleteHomeworkSubmission() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("homeworkText", "Sample Text");

        Mockito.lenient().when(studentService.deleteHomeworkSubmission(Mockito.anyInt(), Mockito.anyLong())).thenReturn(true);

        ResponseEntity<?> responseEntity = studentController.deleteHomeworkSubmission(1, 2L);

        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }



    @Test
    void updateHomeworkSubmissionCustomWithId() {
        Integer studentId = 1;
        Integer submissionId = 1;
        boolean updateById = true;
        Map<String, Object> request = Collections.singletonMap("updatedText", "Updated Text");

        ResponseEntity<String> responseEntity = studentController.updateHomeworkSubmissionCustomWithId(studentId, submissionId, updateById, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Updated homework submission with custom logic and ID", responseEntity.getBody());
    }

    @Test
    void updateHomeworkSubmissionCustomWithoutId() {
        Integer studentId = 1;
        Integer submissionId = 1;
        Map<String, Object> request = Collections.singletonMap("updatedText", "Updated Text");

        ResponseEntity<String> responseEntity = studentController.updateHomeworkSubmissionCustomWithoutId(studentId, submissionId, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Updated homework submission with custom logic without ID", responseEntity.getBody());
    }


}