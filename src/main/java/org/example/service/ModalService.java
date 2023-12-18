package org.example.service;

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

    public String generateModalContent(String courseTitle, String professorName, List<String[]> students) throws IOException {
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

    public String generateStudentListHtml(List<String[]> students) {
        StringBuilder html = new StringBuilder("<tbody>");

        for (int i = 0; i < students.size(); i++) {
            String[] student = students.get(i);
            String checkboxId = "attendanceCheckbox" + i;

            html.append("<tr>")
                    .append("<td>").append(student[0]).append("</td>")
                    .append("<td>").append(student[1]).append("</td>")
                    .append("<td>")
                    .append("<div class=\"form-check\">")
                    .append("<input class=\"form-check-input\" type=\"checkbox\" id=\"").append(checkboxId).append("\"");

            if (Boolean.parseBoolean(student[2])) {
                html.append(" checked=\"checked\"");
            }

            html.append(">")
                    .append("</div>")
                    .append("</td>")
                    .append("<td>")
                    .append("<select class=\"form-select\" aria-label=\"Grade\">")
                    .append(generateGradeOptions(Integer.parseInt(student[3])))
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
