package org.example.test;

import org.example.model.Course;
import org.example.model.Student;
import org.example.model.User;
import org.example.repository.CourseRepository;
import org.example.repository.StudentRepository;
import org.example.repository.UserRepository;
import org.example.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private CourseService courseService;

    public CourseServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.courseService = new CourseService(courseRepository, userRepository, studentRepository);
    }

    @Test
    void getAllOptionalCourses() {
        // Mocking
        List<Object[]> mockCourseResults = new ArrayList<>();
        Object[] mockRow = new Object[]{"Course1", true, "John", "Doe", "Monday", Time.valueOf("10:00:00")};
        mockCourseResults.add(mockRow);
        when(courseRepository.findOptionalCourses()).thenReturn(mockCourseResults);

        // Test
        String response = courseService.getAllOptionalCourses();

        // Assertion
        String expectedResponse = "<tr><td><input type='checkbox' name='ids' value='Course1'></td><td>Course1</td><td>true</td><td>John Doe</td><td>Monday</td><td>10:00:00</td></tr>";
        assertEquals(expectedResponse, response);
        verify(courseRepository, times(1)).findOptionalCourses();
    }

    @Test
    void getCurrentEnrolledCourses() {
        // Mocking
        List<Object[]> mockCourseResults = new ArrayList<>();
        Object[] mockRow = new Object[]{"Course1", true, "John", "Doe", "Monday", Time.valueOf("10:00:00")};
        mockCourseResults.add(mockRow);
        when(courseRepository.findEnrolledCoursesOfStudent("john@example.com")).thenReturn(mockCourseResults);

        // Test
        String response = courseService.getCurrentEnrolledCourses("john@example.com");

        // Assertion
        String expectedResponse = "<tr><td><input type='checkbox' name='ids' value='Course1'></td><td>Course1</td><td>true</td><td>John Doe</td><td>Monday</td><td>10:00:00</td></tr>";
        assertEquals(expectedResponse, response);
        verify(courseRepository, times(1)).findEnrolledCoursesOfStudent("john@example.com");
    }

    @Test
    void addListOfCourses() {
        // Mocking
        User user = new User();
        Student student = new Student();
        student.setUser(user);

        Course course = new Course();
        List<Student> students = new ArrayList<>();
        students.add(student);
        course.setStudents(students);

        List<Course> courses = new ArrayList<>();
        courses.add(course);
        student.setCourses(courses);

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);
        when(studentRepository.findByUser(user)).thenReturn(student);
        when(courseRepository.findByCourseName("Course1")).thenReturn(course);

        // Test
        boolean result = courseService.addListOfCourses(List.of("Course1"), "john@example.com");

        // Assertion
        assertTrue(result);
        verify(userRepository, times(1)).findByEmail("john@example.com");
        verify(studentRepository, times(1)).findByUser(user);
        verify(courseRepository, times(1)).findByCourseName("Course1");
        verify(studentRepository, times(1)).save(student);
        verify(courseRepository, times(1)).save(course);
    }
}
