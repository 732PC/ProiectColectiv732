package org.example.controller;

import org.example.model.Attendance;
import org.example.model.StudentCourse;
import org.example.service.StudentCourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNull;

@ExtendWith(MockitoExtension.class)
class StudentCourseControllerTest {

    @InjectMocks
    private StudentCourseController studentCourseController;

    @Mock
    private StudentCourseService studentCourseService;

    @Test
    void getList_ValidCourseId_ReturnsList() {
        // Mocking
        List<StudentCourse> studentCourses = Arrays.asList(
                new StudentCourse(),
                new StudentCourse()
        );
        when(studentCourseService.getAllById(1)).thenReturn(studentCourses);

        // Test
        ResponseEntity<List<StudentCourse>> response = studentCourseController.getList(1);

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentCourses, response.getBody());
        verify(studentCourseService, times(1)).getAllById(1);
    }

    @Test
    void getList_InvalidCourseId_ReturnsNotFound() {
        // Mocking
        when(studentCourseService.getAllById(1)).thenReturn(null);

        // Test
        ResponseEntity<List<StudentCourse>> response = studentCourseController.getList(1);

        // Assertion
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(null, response.getBody());
        verify(studentCourseService, times(1)).getAllById(1);
    }

    @Test
    void updateNota_ValidParameters_ReturnsStudentCourse() {
        // Mocking
        StudentCourse studentCourse = new StudentCourse();
        when(studentCourseService.addNote(1, 1, 8.5)).thenReturn(studentCourse);

        // Test
        ResponseEntity<StudentCourse> response = studentCourseController.updateNota(1, 1, 8.5);

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentCourse, response.getBody());
        verify(studentCourseService, times(1)).addNote(1, 1, 8.5);
    }

    @Test
    void updateNota_InvalidParameters_ReturnsNotFound() {
        // Mocking
        double nota = -1; // Invalid nota
        when(studentCourseService.addNote(1, 1, nota)).thenReturn(null);

        // Test
        ResponseEntity<StudentCourse> response = studentCourseController.updateNota(1, 1, nota);

        // Assertion
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(null, response.getBody());
        verify(studentCourseService, times(1)).addNote(1, 1, nota);
    }

    @Test
    void updateAttendance_ValidParameters_ReturnsStudentCourse() {
        // Mocking

        StudentCourse studentCourse = new StudentCourse();
        when(studentCourseService.addAttendance(1, 1, Attendance.PRZ)).thenReturn(studentCourse);

        // Test
        ResponseEntity<StudentCourse> response = studentCourseController.updateAttendance(1, 1, Attendance.PRZ);

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentCourse, response.getBody());
        verify(studentCourseService, times(1)).addAttendance(1, 1, Attendance.PRZ);
    }

    @Test
    void updateAttendance_InvalidParameters_ReturnsNotFound() {
        // Mocking
        Attendance attendance = null; // Invalid attendance
        when(studentCourseService.addAttendance(1, 1, attendance)).thenReturn(null);

        // Test
        ResponseEntity<StudentCourse> response = studentCourseController.updateAttendance(1, 1, attendance);

        // Assertion
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(null, response.getBody());
        verify(studentCourseService, times(1)).addAttendance(1, 1, attendance);
    }
}
