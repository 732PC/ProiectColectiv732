package org.example.test;

import org.example.controller.CourseController;
import org.example.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourseControllerTest {

    @Test
    void getRouter_InvalidType_ReturnsIamATeapot() {
        // Mocking
        CourseService courseService = mock(CourseService.class);

        // Test
        ResponseEntity<String> response = new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
        CourseController courseController = new CourseController(courseService);
        ResponseEntity<String> result = courseController.getRouter("invalidType", "user@example.com");

        // Assertion
        assertEquals(response.getStatusCode(), result.getStatusCode());
        assertEquals(response.getBody(), result.getBody());
        verify(courseService, never()).getAllOptionalCourses();
        verify(courseService, never()).getCurrentEnrolledCourses("user@example.com");
    }

    @Test
    void addCourses_Successful_ReturnsOK() {
        // Mocking
        CourseService courseService = mock(CourseService.class);
        when(courseService.addListOfCourses(anyList(), eq("user@example.com"))).thenReturn(true);

        // Inject the mocked CourseService into the CourseController
        CourseController courseController = new CourseController(courseService);

        // Test
        ResponseEntity<String> result = courseController.addCourses(new ArrayList<>(), "user@example.com");

        // Assertion
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("All good!", result.getBody()); // Update the expected result
        verify(courseService, times(1)).addListOfCourses(anyList(), eq("user@example.com"));
    }


    @Test
    void addCourses_Unsuccessful_ReturnsNotImplemented() {
        // Mocking
        CourseService courseService = mock(CourseService.class);
        when(courseService.addListOfCourses(anyList(), eq("user@example.com"))).thenReturn(false);

        // Test
        ResponseEntity<String> response = new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
        CourseController courseController = new CourseController(courseService);
        ResponseEntity<String> result = courseController.addCourses(new ArrayList<>(), "user@example.com");

        // Assertion
        assertEquals(response.getStatusCode(), result.getStatusCode());
        assertEquals(response.getBody(), result.getBody());
        verify(courseService, times(1)).addListOfCourses(anyList(), eq("user@example.com"));
    }
}
