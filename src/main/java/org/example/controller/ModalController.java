package org.example.controller;

import org.example.service.ModalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
public class ModalController {

    private final ModalService modalService;

    @Autowired
    public ModalController(ModalService modalService) {
        this.modalService = modalService;
    }

    @GetMapping("/attendances-modal-content")
    public String getAttendancesModalContent() throws IOException {
        String courseTitle = "Introduction to Programming";
        String professorName = "John Doe";

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
