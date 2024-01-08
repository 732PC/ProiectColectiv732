package org.example.Tests.service;

import org.example.Key.StudentCourseKey;
import org.example.model.*;
import org.example.repository.CourseMaterialRepository;
import org.example.repository.CourseRepository;
import org.example.repository.StudentCourseRepository;
import org.example.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseMaterialRepository courseMaterialRepository;
    @Mock
    private StudentCourseRepository studentCourseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void init() {
        course = new Course();
    }

    @Test
    void getAllCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(new Course(), new Course()));
        List<Course> courses = courseService.getAllCourses();
        assertNotNull(courses);
        assertEquals(2, courses.size());
    }

    @Test
    void getCourseById() {
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        assertEquals(Optional.of(course), courseService.getCourseById(1));
    }

    @Test
    void addCourses() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        Course addedCourse = courseService.addCourses(course);
        assertNotNull(addedCourse);
        assertEquals(course, addedCourse);
    }

    @Test
    void deleteCourse() {
        when(courseRepository.findById(1)).thenReturn(Optional.of(new Course()));
        boolean deleted = courseService.deleteCourse(1);
        assertTrue(deleted);
        verify(courseRepository, times(1)).deleteById(1);
    }

    @Test
    public void addCourseMaterialTest() {
        Course course = new Course();
        course.setCourseID(1);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        CourseMaterial courseMaterial = new CourseMaterial();
        courseMaterial.setMaterialId(1L);
        when(courseMaterialRepository.save(any())).thenReturn(courseMaterial);
        CourseMaterialResponse response = courseService.addCourseMaterial(1, "test", "test");
        assertNotNull(response);
        assertEquals(1L, response.getMaterialId());
        verify(courseMaterialRepository, times(1)).save(any());
    }

    @Test
    public void deleteCourseMaterialTest() {
        Course course = new Course();
        course.setCourseID(1);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        CourseMaterial courseMaterial = new CourseMaterial();
        courseMaterial.setMaterialId(1L);
        course.getCourseMaterials().add(courseMaterial);
        assertTrue(courseService.deleteCourseMaterial(1, 1L));
        assertFalse(courseService.deleteCourseMaterial(3, 1L));
    }

    @Test
    public void getStudentEmailsByCourseTest() {
        StudentCourse studCourse = new StudentCourse();
        Students stud = new Students();
        stud.setEmail("test");
        Course course = new Course();
        course.setCourseID(1);
        studCourse.setCourse(course);
        studCourse.setStudent(stud);
        when(studentCourseRepository.findAll()).thenReturn(List.of(studCourse));
        assertEquals(List.of("test"), courseService.getStudentEmailsByCourse(1));
    }
}
