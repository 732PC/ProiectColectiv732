package org.example.controller;

import org.example.controller.CourseAllocationController;
import org.example.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

class CourseAllocationControllerTest {

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private CourseAllocationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAssignCoursesToStudentForStudyYear() {
        int studyYear = 2023;


        doNothing().when(enrollmentService).assignRequiredCoursesToStudentForStudyYear(anyInt());


        ResponseEntity<String> responseEntity = controller.assignCoursesToStudentForStudyYear(studyYear);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Courses assigned successfully for the specified study year", responseEntity.getBody());
    }

    @Test
    void testAssignCoursesAutomatically() {

        doNothing().when(enrollmentService).assignRequiredCoursesToStudentAutomatically();


        ResponseEntity<String> responseEntity = controller.assignCoursesAutomatically();


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Courses assigned automatically for the specified study year", responseEntity.getBody());
    }

}

