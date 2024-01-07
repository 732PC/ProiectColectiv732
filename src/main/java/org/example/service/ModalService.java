package org.example.service;

import org.example.model.Attendance;
import org.example.model.StudentCourse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ModalService {

    public String generateModalContent(String courseTitle, String professorName, List<StudentCourse> students) throws IOException {
        ClassPathResource resource = new ClassPathResource("/static/attendances-content.html");

        try (InputStream inputStream = resource.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String modalContentTemplate = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));

            modalContentTemplate = modalContentTemplate.replace("{courseTitle}", courseTitle);
            modalContentTemplate = modalContentTemplate.replace("{professorName}", professorName);

            String studentListHtml = generateStudentListHtml(students);
            modalContentTemplate = modalContentTemplate.replace("{studentList}", studentListHtml);

            return modalContentTemplate;
        }
    }

    public String generateStudentListHtml(List<StudentCourse> students) {
        StringBuilder html = new StringBuilder("<tbody>");

        for (int i = 0; i < students.size(); i++) {
            StudentCourse student = students.get(i);
            String checkboxId = "attendanceCheckbox" + i;

            html.append("<tr>")
                    .append("<td>")
                    .append("<input type=\"hidden\" name=\"studentId\" value=\"").append(student.getStudent().getStudentID()).append("\" data-student-id=\"").append(student.getStudent().getStudentID()).append("\" />")
                    .append(student.getStudent().getLastname())
                    .append("</td>")
                    .append("<td>")
                    .append("<input type=\"hidden\" name=\"courseId\" value=\"").append(student.getCourse().getCourseID()).append("\" data-course-id=\"").append(student.getCourse().getCourseID()).append("\" />")
                    .append(student.getStudent().getFirstname())
                    .append("</td>")
                    .append("<td>")
                    .append("<div class=\"form-check\">")
                    .append("<input class=\"form-check-input\" type=\"checkbox\" id=\"").append(checkboxId).append("\" data-attendance=\"").append(student.getAttendance()).append("\"");

            if (student.getAttendance() == Attendance.PRZ) {
                html.append(" checked=\"checked\"");
            }

            html.append(">")
                    .append("</div>")
                    .append("</td>")
                    .append("<td>")
                    .append("<select class=\"form-select\" aria-label=\"Grade\" data-grade=\"").append(student.getNote()).append("\">")
                    .append(generateGradeOptions((int) student.getNote()))
                    .append("</select>")
                    .append("</td>")
                    .append("</tr>");
        }

        html.append("</tbody>");

        return html.toString();
    }



    public String generateGradeOptions(int selectedGrade) {
        StringBuilder options = new StringBuilder();
        IntStream.rangeClosed(1, 10).forEach(i -> {
            options.append("<option value=\"").append(i).append("\"");

            if (i == selectedGrade) {
                options.append(" selected");
            }

            options.append(">").append(i).append("</option>");
        });

        return options.toString();
    }
}
