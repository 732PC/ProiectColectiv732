package service;

import org.example.Controller.CourseAllocationController;
import org.example.Service.EnrollmentService;
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
import static org.mockito.Mockito.when;

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
    void testAssignCoursesAutomatically_Success() {
        int studyYear = 2023;


        doNothing().when(enrollmentService).assignRequiredCoursesToStudentAutomatically(anyInt());


        ResponseEntity<String> responseEntity = controller.assignCoursesAutomatically(studyYear);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Courses assigned automatically for the specified study year", responseEntity.getBody());
    }

    @Test
    void testAssignCoursesAutomatically_Error() {
        int studyYear = 2023;
        String errorMessage = "Some error occurred";


        doThrow(new RuntimeException(errorMessage)).when(enrollmentService).assignRequiredCoursesToStudentAutomatically(anyInt());


        ResponseEntity<String> responseEntity = controller.assignCoursesAutomatically(studyYear);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error assigning courses: " + errorMessage, responseEntity.getBody());
    }
}