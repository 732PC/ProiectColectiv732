package org.example.service;

import org.example.model.Attendance;
import org.example.model.StudentCourse;
import org.example.repository.StudentCourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentCourseServiceTest {

    @InjectMocks
    private StudentCourseService studentCourseService;

    @Mock
    private StudentCourseRepository studentCourseRepository;

    @Test
    void getAllById_ValidCourseId_ReturnsStudentCourses() {
        int courseId = 1;
        List<StudentCourse> studentCourses = Collections.singletonList(new StudentCourse());
        when(studentCourseRepository.findAllByCourse_CourseID(courseId)).thenReturn(studentCourses);

        List<StudentCourse> result = studentCourseService.getAllById(courseId);

        assertNotNull(result);
        assertEquals(studentCourses, result);
        verify(studentCourseRepository, times(1)).findAllByCourse_CourseID(courseId);
    }

    @Test
    void addNote_ValidInputsAndExistingStudentCourse_ReturnsUpdatedStudentCourse() {
        StudentCourse existingStudentCourse = new StudentCourse();
        existingStudentCourse.setNote(8.0);

        when(studentCourseRepository.findByCourseCourseIDAndStudentStudentID(1, 1))
                .thenReturn(existingStudentCourse);
        when(studentCourseRepository.save(existingStudentCourse)).thenReturn(existingStudentCourse);

        StudentCourse result = studentCourseService.addNote(1, 1, 9.5);

        assertNotNull(result);
        assertEquals(9.5, result.getNote());
        verify(studentCourseRepository, times(1)).findByCourseCourseIDAndStudentStudentID(1, 1);
        verify(studentCourseRepository, times(1)).save(existingStudentCourse);
    }

    @Test
    void addNote_InvalidNote_ReturnsNull() {
        StudentCourse result = studentCourseService.addNote(1, 1, -1);

        assertNull(result);
        verify(studentCourseRepository, never()).findByCourseCourseIDAndStudentStudentID(1, 1);
        verify(studentCourseRepository, never()).save(any());
    }

    @Test
    void addNote_NonExistingStudentCourse_ReturnsNull() {

        when(studentCourseRepository.findByCourseCourseIDAndStudentStudentID(1, 1))
                .thenReturn(null);

        StudentCourse result = studentCourseService.addNote(1, 1, 9.5);

        assertNull(result);
        verify(studentCourseRepository, times(1)).findByCourseCourseIDAndStudentStudentID(1, 1);
        verify(studentCourseRepository, never()).save(any());
    }

    @Test
    void addAttendance_ValidInputsAndExistingStudentCourse_ReturnsUpdatedStudentCourse() {

        StudentCourse existingStudentCourse = new StudentCourse();

        when(studentCourseRepository.findByCourseCourseIDAndStudentStudentID(1, 1))
                .thenReturn(existingStudentCourse);
        when(studentCourseRepository.save(existingStudentCourse)).thenReturn(existingStudentCourse);

        StudentCourse result = studentCourseService.addAttendance(1, 1, Attendance.PRZ);

        assertNotNull(result);
        assertEquals(Attendance.PRZ, result.getAttendance());
        verify(studentCourseRepository, times(1)).findByCourseCourseIDAndStudentStudentID(1, 1);
        verify(studentCourseRepository, times(1)).save(existingStudentCourse);
    }

    @Test
    void addAttendance_NonExistingStudentCourse_ReturnsNull() {

        when(studentCourseRepository.findByCourseCourseIDAndStudentStudentID(1, 1))
                .thenReturn(null);

        StudentCourse result = studentCourseService.addAttendance(1, 1, Attendance.PRZ);

        assertNull(result);
        verify(studentCourseRepository, times(1)).findByCourseCourseIDAndStudentStudentID(1, 1);
        verify(studentCourseRepository, never()).save(any());
    }
}
