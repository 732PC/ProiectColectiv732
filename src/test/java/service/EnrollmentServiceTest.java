package service;

import org.example.Model.Course;
import org.example.Model.Students;
import org.example.Repository.studentsRepository;
import org.example.Repository.courseRepository;
import org.example.Service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {
    @Mock
    private studentsRepository studentsRepository;
    @Mock
    private courseRepository courseRepository;





    @InjectMocks
    private EnrollmentService enrollmentService;
    private Students students;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @BeforeEach
    void init() {
        students = new Students();
    }


    @Test
    void assignRequiredCoursesToStudentForStudyYear() {
        int studyYear = 2023;
        List<Students> studentsInYear = Arrays.asList(students);
        List<Course> requiredCourses = Arrays.asList(new Course());

        when(studentsRepository.findRequiredStudentsByStudyYear(studyYear)).thenReturn(studentsInYear);
        when(courseRepository.findRequiredCoursesByStudyYear(studyYear)).thenReturn(requiredCourses);

        enrollmentService.assignRequiredCoursesToStudentForStudyYear(studyYear);

        verify(studentsRepository, times(1)).save(students);
    }

    @Test
    void assignRequiredCoursesToStudentAutomatically() {
        // Mock data
        Students student1 = new Students();
        student1.setStudyYear(1);

        Students student2 = new Students();
        student2.setStudyYear(2);

        List<Students> allStudents = Arrays.asList(student1, student2);

        Course course1 = new Course();
        course1.setStudyYear(1);

        Course course2 = new Course();
        course2.setStudyYear(2);

        List<Course> allCourses = Arrays.asList(course1, course2);

        // Mock repository calls
        when(studentsRepository.findAll()).thenReturn(allStudents);
        when(courseRepository.findAll()).thenReturn(allCourses);
        when(courseRepository.findRequiredCoursesByStudyYear(1)).thenReturn(Arrays.asList(course1));
        when(courseRepository.findRequiredCoursesByStudyYear(2)).thenReturn(Arrays.asList(course2));

        // Call the method to be tested
        enrollmentService.assignRequiredCoursesToStudentAutomatically();

        // Ensure that the save method is called for each student
        verify(studentsRepository, times(1)).save(student1);
        verify(studentsRepository, times(1)).save(student2);
    }




    @Test
    void assignCoursesToStudentAvoidOverallocation() {
        List<Course> courses = Arrays.asList(new Course());

        // Mock the behavior of studentRepo
        when(studentsRepository.save(any())).thenReturn(null);

        // Call the method to be tested
        enrollmentService.assignCoursesToStudentAvoidOverallocation(students, courses);

        // Verify that the save method is called on the studentRepo
        verify(studentsRepository, times(1)).save(students);
    }



}