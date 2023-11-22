package org.example.services;

import org.example.models.Students;
import org.example.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;



import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(new Students(), new Students()));

        assertEquals(2, studentService.getAllStudents().size());
    }



    @Test
    void getStudentById() {
        int studentId = 1;
        Students student = new Students();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        assertEquals(Optional.of(student), studentService.getStudentById(studentId));
    }

//    @Test
//    void addStudent() {
//        Students student = new Students();
//        when(studentRepository.save(student)).thenReturn(student);
//
//        assertEquals(student, studentService.addStudent(student));
//    }

    @Test
    void updateStudent() {
        int studentId = 3;
        Students existingStudent = new Students();
        Students updatedStudent = new Students();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Students.class))).thenAnswer(invocation -> {
            Students savedStudent = invocation.getArgument(0);
            savedStudent.setId(studentId);
            return savedStudent;
        });

        Optional<Students> result = studentService.updateStudent(studentId, updatedStudent);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(studentId);
    }


    @Test
    void deleteStudent() {
        int studentId = 1;

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Students()));

        boolean result = studentService.deleteStudent(studentId);

        assertTrue(result);

        verify(studentRepository, times(1)).findById(studentId);

        verify(studentRepository, times(1)).deleteById(studentId);
    }


    @Test
    void deleteStudentNotFound() {
        int studentId = 1;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.deleteStudent(studentId));


        verify(studentRepository, times(1)).findById(studentId);

        verify(studentRepository, never()).deleteById(studentId);
    }


}
