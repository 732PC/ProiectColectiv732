package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.*;
import org.example.service.CourseService;
import org.example.service.EmailService;
import org.example.service.ProfessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CourseController.class)
@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private CourseController courseController;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CourseService courseService;
    @MockBean
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void init() {

        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    public void testGetAllCourses() throws Exception {
        List<Course> coursesList = Arrays.asList(
                new Course(),
                new Course()
        );

        given(courseService.getAllCourses()).willReturn(coursesList);

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void testGetCourseById() throws Exception {
        Course course = new Course();

        given(courseService.getCourseById(1)).willReturn(Optional.of(course));

        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk());

    }

    @Test
    public void testAddCourse() throws Exception {
        Course courseToAdd = new Course();

        given(courseService.addCourses(any(Course.class))).willReturn(courseToAdd);

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(courseToAdd)))
                .andExpect(status().isCreated());

    }


//    @Test
//    void addCourseMaterial() throws Exception {
//        CourseMaterialResponse response = new CourseMaterialResponse();
//        response.setMaterialId(1L);
//        Course course = new Course();
//        course.setCourseID(1);
//        CourseMaterial courseMaterial = new CourseMaterial(1L, "test", "test", null);
//        when(courseService.addCourseMaterial(any(Integer.class), any(String.class), any(String.class))).
//                thenReturn(response);
//
//        ResponseEntity<?> responseEntity = courseController.addCourseMaterial()
//
//        mockMvc.perform(post("/courses/1/course-materials").contentType(MediaType.APPLICATION_JSON).
//                content(new ObjectMapper().writeValueAsString(courseMaterial))).andExpect(status().isCreated());
//    }

    @Test
    public void addCourseMaterialTest() throws Exception {
        Integer courseId = 1;
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("content", "content");
        requestBody.put("title", "title");
        requestBody.put("course", "null");

        when(courseService.addCourseMaterial(courseId, "content", "title")).thenReturn(new CourseMaterialResponse());

        when(courseService.getCourseById(any(Integer.class))).thenReturn(Optional.of(new Course()));
        when(emailService.configureEmailTemplateCourseMaterials(any(String.class), any(String.class))).thenReturn("test");
        when(courseService.getStudentEmailsByCourse(any(Integer.class))).thenReturn(new ArrayList<>());
        doNothing().when(emailService).sendEmailFromTemplate(any(String.class), any(String.class), any(String.class));

        ResponseEntity<?> result = courseController.addCourseMaterial(courseId, requestBody);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(courseService, times(1)).addCourseMaterial(courseId, "content", "title");
    }


    @Test
    void deleteCourseMaterial() {
    }
}


