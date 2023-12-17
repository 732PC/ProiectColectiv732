package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.HomeworkSubmission;
import org.example.model.HomeworkSubmissionResponse;
import org.example.model.Students;
import org.example.repository.HomeworkSubmissionRepository;
import org.example.repository.StudentRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private HomeworkSubmissionRepository homeworkSubmissionRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        Students student1 = new Students();
        Students student2 = new Students();
        List<Students> studentsList = Arrays.asList(student1, student2);

        when(studentRepository.findAll()).thenReturn(studentsList);

        List<Students> result = studentService.getAllStudents();

        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById() {
        Students student = new Students();
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        Optional<Students> result = studentService.getStudentById(1);

        assertTrue(result.isPresent());
        verify(studentRepository, times(1)).findById(1);
    }

    @Test
    void testGetStudentByIdNotFound() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Students> result = studentService.getStudentById(1);

        assertTrue(result.isEmpty());
        verify(studentRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateStudent() {
        Students existingStudent = new Students();
        existingStudent.setFirstname("John");

        Students updatedStudent = new Students();
        updatedStudent.setFirstname("Doe");

        when(studentRepository.findById(1)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(updatedStudent);

        Optional<Students> result = studentService.updateStudent(1, updatedStudent);

        assertTrue(result.isPresent());
        assertEquals("Doe", result.get().getFirstname());
        verify(studentRepository, times(1)).findById(1);
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    void testUpdateStudentNotFound() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Students> result = studentService.updateStudent(1, new Students());

        assertTrue(result.isEmpty());
        verify(studentRepository, times(1)).findById(1);
        verify(studentRepository, never()).save(any());
    }

    @Test
    void testDeleteStudent() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(new Students()));

        assertTrue(studentService.deleteStudent(1));
        verify(studentRepository, times(1)).findById(1);
        verify(studentRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteStudentNotFound() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.deleteStudent(1));
        verify(studentRepository, times(1)).findById(1);
        verify(studentRepository, never()).deleteById(1);
    }

    @Test
    void testAddStudentValidCnp() {
        Students student = new Students();
        student.setCnp("1234567890123");

        when(studentRepository.save(any())).thenReturn(student);

        Students result = studentService.addStudent("John", "Doe", "1234567890123",
                LocalDate.now(), 1, "Bachelor", "Funding", "High School");

        assertNotNull(result);
        verify(studentRepository, times(1)).save(any());
    }

    @Test
    void testAddStudentInvalidCnp() {
        assertThrows(IllegalArgumentException.class, () ->
                studentService.addStudent("John", "Doe", "1234567890",
                        LocalDate.now(), 1, "Bachelor", "Funding", "High School"));
        verify(studentRepository, never()).save(any());
    }
    @Test
    void testAddHomeworkSubmission() {
        Students student = new Students();
        student.setStudentID(1);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        HomeworkSubmission savedSubmission = new HomeworkSubmission();
        savedSubmission.setSubmissionId(1L);

        when(homeworkSubmissionRepository.save(any())).thenReturn(savedSubmission);

        HomeworkSubmissionResponse response = studentService.addHomeworkSubmission(1, "Homework text");

        assertNotNull(response);
        assertEquals(1, response.getSubmissionId());
        verify(studentRepository, times(1)).findById(1);
        verify(homeworkSubmissionRepository, times(1)).save(any());
    }

    @Test
    void testAddHomeworkSubmissionStudentNotFound() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                studentService.addHomeworkSubmission(1, "Homework text"));

        verify(studentRepository, times(1)).findById(1);
        verify(homeworkSubmissionRepository, never()).save(any());
    }

    @Test
    void testAddHomeworkSubmissionEmptyHomeworkSubmissionsList() {
        Students student = new Students();
        student.setStudentID(1);
        student.setHomeworkSubmissions(new ArrayList<>());

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        HomeworkSubmission savedSubmission = new HomeworkSubmission();
        savedSubmission.setSubmissionId(1L);

        when(homeworkSubmissionRepository.save(any())).thenReturn(savedSubmission);

        HomeworkSubmissionResponse response = studentService.addHomeworkSubmission(1, "Homework text");

        assertNotNull(response);
        assertEquals(1, response.getSubmissionId());
        verify(studentRepository, times(1)).findById(1);
        verify(homeworkSubmissionRepository, times(1)).save(any());
    }

    @Test
    void testAddHomeworkSubmissionException() {
        Students student = new Students();
        student.setStudentID(1);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(homeworkSubmissionRepository.save(any())).thenThrow(new RuntimeException("Test exception"));

        assertThrows(RuntimeException.class, () ->
                studentService.addHomeworkSubmission(1, "Homework text"));

        verify(studentRepository, times(1)).findById(1);
        verify(homeworkSubmissionRepository, times(1)).save(any());
    }

    @Test
    public void testDeleteHomeworkSubmission() {
        Students student = new Students();
        student.setStudentID(1);
        HomeworkSubmission submission = new HomeworkSubmission();
        submission.setSubmissionId(2L);
        submission.setSubmissionText("Sample Text");
        submission.setStudent(student);

        Mockito.lenient().when(studentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(student));
        Mockito.lenient().when(homeworkSubmissionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(submission));

        boolean deleted = studentService.deleteHomeworkSubmission(1, 2L);

        Assert.assertTrue(deleted);
    }



    @Test
    void deleteHomeworkSubmissionNotFound() {
        Integer studentId = 1;
        Long submissionId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        boolean result = studentService.deleteHomeworkSubmission(studentId, submissionId);

        assertFalse(result);
        verify(studentRepository, times(1)).findById(studentId);
        verify(homeworkSubmissionRepository, never()).deleteById(submissionId);
    }


    @Test
    void testUpdateHomeworkSubmissionStudentNotFound() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        Optional<HomeworkSubmission> result = studentService.updateHomeworkSubmission(1, 1L, "Updated Text");

        assertFalse(result.isPresent());
        verify(studentRepository, times(1)).findById(1);
        verify(homeworkSubmissionRepository, never()).findById(anyLong());
        verify(homeworkSubmissionRepository, never()).save(any());
    }

    @Test
    void testGetAllHomeworkSubmissions() {
        Students student = new Students();
        student.setStudentID(1);

        HomeworkSubmission submission1 = new HomeworkSubmission();
        submission1.setSubmissionId(1L);
        submission1.setSubmissionText("Homework 1");

        HomeworkSubmission submission2 = new HomeworkSubmission();
        submission2.setSubmissionId(2L);
        submission2.setSubmissionText("Homework 2");

        student.setHomeworkSubmissions(Arrays.asList(submission1, submission2));

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        List<HomeworkSubmission> result = studentService.getAllHomeworkSubmissions(1);

        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findById(1);
    }





    @Test
    void testGetHomeworkSubmissionByIdNotFound() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                studentService.getHomeworkSubmissionById(1, 1));

        verify(studentRepository, times(1)).findById(1);
    }
    @Test
    void testUpdateHomeworkSubmissionByIdNotFound() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        Optional<HomeworkSubmission> result = studentService.updateHomeworkSubmissionById(1, 1, "Updated Text");

        assertFalse(result.isPresent());
        verify(studentRepository, times(1)).findById(1);
        verify(homeworkSubmissionRepository, never()).save(any());
    }

//    @Test
//    void testGetHomeworkSubmissionById() {
//        Students student = new Students();
//        student.setStudentID(1);
//
//        HomeworkSubmission submission1 = new HomeworkSubmission();
//        submission1.setSubmissionId(1L);
//        submission1.setSubmissionText("Homework 1");
//
//        HomeworkSubmission submission2 = new HomeworkSubmission();
//        submission2.setSubmissionId(2L);
//        submission2.setSubmissionText("Homework 2");
//
//        student.setHomeworkSubmissions(Arrays.asList(submission1, submission2));
//
//        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
//
//        Optional<HomeworkSubmission> result = studentService.getHomeworkSubmissionById(1, 1);
//
//        assertTrue(result.isPresent(), "Expected a submission to be present");
//        assertEquals("Homework 1", result.get().getSubmissionText(), "Unexpected submission text");
//        verify(studentRepository, times(1)).findById(1);
//    }
//
//    @Test
//    void testUpdateHomeworkSubmissionById() {
//        Students student = new Students();
//        student.setStudentID(1);
//
//        HomeworkSubmission submission = new HomeworkSubmission();
//        submission.setSubmissionId(1L);
//        submission.setSubmissionText("Homework Text");
//
//        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
//        when(homeworkSubmissionRepository.save(any())).thenReturn(submission);
//
//        Optional<HomeworkSubmission> result = studentService.updateHomeworkSubmissionById(1, 1, "Updated Text");
//
//        assertTrue(result.isPresent(), "Expected an updated submission to be present");
//        assertEquals("Updated Text", result.get().getSubmissionText(), "Unexpected updated submission text");
//        verify(studentRepository, times(1)).findById(1);
//        verify(homeworkSubmissionRepository, times(1)).save(any());
//    }

}

