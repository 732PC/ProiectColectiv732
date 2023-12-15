package service;

import org.example.Model.Course;
import org.example.Model.Students;
import org.example.Repository.courseRepo;
import org.example.Repository.studentRepo;
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
public class EnrollmentServiceTest {
    @Mock
    private studentRepo studentRepo;
    @Mock
    private courseRepo courseRepository;


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

        when(studentRepo.findRequiredStudentsByStudyYear(studyYear)).thenReturn(studentsInYear);
        when(courseRepository.findRequiredCoursesByStudyYear(studyYear)).thenReturn(requiredCourses);

        enrollmentService.assignRequiredCoursesToStudentForStudyYear(studyYear);

        verify(studentRepo, times(1)).save(students);
    }

    @Test
    void assignRequiredCoursesToStudentAutomatically() {
        int studyYear = 2023;
        List<Students> allStudents = Arrays.asList(students);
        List<Course> requiredCourses = Arrays.asList(new Course());

        when(studentRepo.findAll()).thenReturn(allStudents);
        when(courseRepository.findRequiredCoursesByStudyYear(studyYear)).thenReturn(requiredCourses);

        enrollmentService.assignRequiredCoursesToStudentAutomatically(studyYear);

        verify(studentRepo, times(1)).save(students);
    }

    @Test
    void assignCoursesToStudentAvoidOverallocation() {
        List<Course> courses = Arrays.asList(new Course());

        enrollmentService.assignCoursesToStudentAvoidOverallocation(students, courses);

        verify(studentRepo, times(1)).save(students);
    }



}