package org.example.controller;

import org.example.model.Course;
import org.example.service.CourseService;
import org.example.service.ModalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
public class ModalController {

    private final ModalService modalService;
    private final CourseService courseService;

    @Autowired
    public ModalController(ModalService modalService, CourseService courseService) {
        this.modalService = modalService;
        this.courseService = courseService;
    }

    @GetMapping("/attendances-modal-content")
    public String getAttendancesModalContent(@RequestParam("courseId") Integer courseId) throws IOException {
        Course course = courseService.getCourseById(courseId).orElseThrow();

        String courseTitle = course.getName();
        String professorName = courseService.getProfessorFromCourse(course).getLastname();

        List<String[]> students = List.of(
                new String[]{"John", "Doe", "true", "1"},
                new String[]{"Jane", "Smith", "false", "10"},
                new String[]{"John", "Doe", "true", "8"},
                new String[]{"Jane", "Smith", "false", "6"},
                new String[]{"Jane", "Smith", "true", "1"}
        );

        return modalService.generateModalContent(courseTitle, professorName, students);
    }
}
